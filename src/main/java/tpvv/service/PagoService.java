package tpvv.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;               // <-- NUEVO
import org.springframework.data.domain.PageImpl;     // <-- NUEVO
import org.springframework.data.domain.PageRequest;  // <-- NUEVO
import org.springframework.data.domain.Pageable;     // <-- NUEVO
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tpvv.dto.*;
import tpvv.model.*;
import tpvv.repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PagoService {
    Logger logger = LoggerFactory.getLogger(PagoService.class);


    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarjetaPagoRepository tarjetaPagoRepository;

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public String obtenerUrlBack(String apiKey) {
        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
        }
        return comercio.getUrl_back();
    }

    @Transactional(readOnly = true)
    public String obtenerNombreComercio(Long id) {
        // Buscar el Pago por ID
        Pago pago = pagoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Pago no encontrado para el ID proporcionado.")
        );

        // Devolver el nombre del comercio asociado al Pago
        Comercio comercio = pago.getComercio();
        if (comercio == null) {
            throw new IllegalArgumentException("El Pago no tiene un Comercio asociado.");
        }

        return comercio.getNombre();
    }

    @Transactional
    public PedidoCompletoRequest procesarPago(PagoCompletoRequest request, String apiKey) {
        // Obtener PagoData
        PagoData pagoData = request.getPagoData();
        if (pagoData == null) {
            throw new IllegalArgumentException("Error: Falta el objeto PagoData en la petición.");
        }

        // Parsear el importe de String a double
        double importeDouble;
        try {
            importeDouble = Double.parseDouble(pagoData.getImporte());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Importe no válido.");
        }

        // Parsear la fecha de String a Date
        Date fechaDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            fechaDate = sdf.parse(pagoData.getFecha());
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException("Error: Formato de fecha no válido o fecha nula.");
        }

        if (importeDouble <= 0 ||
                pagoData.getTicketExt() == null ||
                pagoData.getFecha() == null) {
            throw new IllegalArgumentException("Error: Datos de PagoData incompletos (importe/ticketExt/fecha).");
        }

        // Obtener TarjetaPagoData
        TarjetaPagoData tarjetaData = request.getTarjetaPagoData();
        if (tarjetaData == null) {
            throw new IllegalArgumentException("Error: Falta el objeto TarjetaPagoData en la petición.");
        }
        if (tarjetaData.getNumeroTarjeta() == null ||
                tarjetaData.getFechaCaducidad() == null ||
                tarjetaData.getNombre() == null) {
            throw new IllegalArgumentException("Error: Datos de TarjetaPagoData incompletos.");
        }

        // Transformar TarjetaPagoData
        int cvcInt;
        try {
            cvcInt = Integer.parseInt(tarjetaData.getCvc());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: CVC no válido.");
        }

        // Parsear fecha de caducidad (String -> Date)
        Date fechaCaducDate;
        try {
            SimpleDateFormat sdfCaduc = new SimpleDateFormat("MM/yy");
            fechaCaducDate = sdfCaduc.parse(tarjetaData.getFechaCaducidad());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error: Formato de fechaCaducidad no válido (se esperaba MM/yy).");
        }

        // Crear entidad TarjetaPago
        TarjetaPago tarjetaPago = new TarjetaPago();
        tarjetaPago.setNumeroTarjeta(tarjetaData.getNumeroTarjeta().replaceAll("\\s+", ""));
        tarjetaPago.setCvc(cvcInt);
        tarjetaPago.setFechaCaducidad(fechaCaducDate);
        tarjetaPago.setNombre(tarjetaData.getNombre().trim().replaceAll("\\s+", " "));

        // Intentar reutilizar si ya existe
        Optional<TarjetaPago> tarjetaExistenteOpt =
                tarjetaPagoRepository.findByNumeroTarjeta(tarjetaPago.getNumeroTarjeta());
        if (tarjetaExistenteOpt.isPresent()) {
            TarjetaPago tarjetaExistente = tarjetaExistenteOpt.get();
            tarjetaExistente.setCvc(tarjetaPago.getCvc());
            tarjetaExistente.setFechaCaducidad(tarjetaPago.getFechaCaducidad());
            tarjetaExistente.setNombre(tarjetaPago.getNombre());
            tarjetaPago = tarjetaExistente;
        }
        tarjetaPagoRepository.save(tarjetaPago);

        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
        }

        // Crear o asignar estado de pago
        EstadoPago estadoPago = obtenerEstadoPago(tarjetaPago.getNumeroTarjeta());

        Optional<EstadoPago> estadoPagoExistenteOpt =
                estadoPagoRepository.findByNombre(estadoPago.getNombre());

        if (estadoPagoExistenteOpt.isPresent()) {
            estadoPago = estadoPagoExistenteOpt.get();
        }

        else  {
            estadoPagoRepository.save(estadoPago);
        }

        // Construir entidad Pago
        Pago pago = new Pago();
        pago.setImporte(importeDouble);
        pago.setTicketExt(pagoData.getTicketExt());
        pago.setFecha(new Date());
        pago.setTarjetaPago(tarjetaPago);
        pago.setEstado(estadoPago);
        pago.setComercio(comercio);

        // Guardar el Pago
        pagoRepository.save(pago);

        // Construir PedidoCompletoRequest para enviarlo al cliente
        PedidoCompletoRequest pedidoCompletoRequest = new PedidoCompletoRequest();
        pedidoCompletoRequest.setId(pago.getId());
        pedidoCompletoRequest.setPagoId(pago.getId());
        pedidoCompletoRequest.setPedidoId(4L);
        pedidoCompletoRequest.setTicketExt(pago.getTicketExt());

        // MODIFICADO: Convertir Date -> String con el mismo formato que esperamos (dd/MM/yyyy HH:mm)
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaStr = sdfOut.format(pago.getFecha());
        pedidoCompletoRequest.setFecha(fechaStr);

        // MODIFICADO: Convertir double -> String
        pedidoCompletoRequest.setImporte(String.valueOf(pago.getImporte()));

        pedidoCompletoRequest.setEstadoPago(pago.getEstado().getNombre());
        pedidoCompletoRequest.setRazonEstadoPago(estadoPago.getRazonEstado());
        pedidoCompletoRequest.setComercioNombre(pago.getComercio().getNombre());
        pedidoCompletoRequest.setNumeroTarjeta(pago.getTarjetaPago().getNumeroTarjeta());

        return pedidoCompletoRequest;
    }


    public EstadoPago obtenerEstadoPago(String numeroTarjeta) {
        String cuatroPrimerosDigitos = numeroTarjeta.substring(0, 4);

        EstadoPago estadoPago = new EstadoPago("default", "default");

        switch (cuatroPrimerosDigitos) {
            case "0000":
                estadoPago.setNombre("RECH0001");
                estadoPago.setRazonEstado("PAGO RECHAZADO: SALDO INSUFICIENTE");
                break;

            case "0001":
                estadoPago.setNombre("RECH0002");
                estadoPago.setRazonEstado("PAGO RECHAZADO: TARJETA BLOQUEADA");
                break;

            case "0002":
                estadoPago.setNombre("RECH0003");
                estadoPago.setRazonEstado("PAGO RECHAZADO: TARJETA VENCIDA");
                break;

            case "0003":
                estadoPago.setNombre("RECH0004");
                estadoPago.setRazonEstado("PAGO RECHAZADO: FALLO EN LA CONEXIÓN CON EL BANCO");
                break;

            case "1000":
                estadoPago.setNombre("PEND0001");
                estadoPago.setRazonEstado("PAGO PENDIENTE: VERIFICACIÓN MANUAL REQUERIDA");
                break;

            case "1001":
                estadoPago.setNombre("PEND0002");
                estadoPago.setRazonEstado("PAGO PENDIENTE: TRANSFERENCIA EN ESPERA DE COMPENSACIÓN");
                break;

            case "1002":
                estadoPago.setNombre("PEND0003");
                estadoPago.setRazonEstado("PAGO PENDIENTE: CONVERSIÓN DE MONEDA EN PROCESO");
                break;

            case "1003":
                estadoPago.setNombre("PEND0004");
                estadoPago.setRazonEstado("PAGO PENDIENTE: PROCESO DE CONCILIACIÓN BANCARIA EN CURSO");
                break;

            case "2000":
                estadoPago.setNombre("ACEPT0001");
                estadoPago.setRazonEstado("PAGO ACEPTADO: IDENTIDAD DEL TITULAR VERIFICADA");
                break;

            case "2001":
                estadoPago.setNombre("ACEPT0002");
                estadoPago.setRazonEstado("PAGO ACEPTADO: REVISIÓN ANTIFRAUDE SUPERADA CON ÉXITO");
                break;

            case "2002":
                estadoPago.setNombre("ACEPT0003");
                estadoPago.setRazonEstado("PAGO ACEPTADO: CONFIRMACIÓN INSTANTÁNEA POR PASARELA DE PAGO");
                break;

            case "2003":
                estadoPago.setNombre("ACEPT0004");
                estadoPago.setRazonEstado("PAGO ACEPTADO: MONEDA SOPORTADA POR EL PROCESADOR DE PAGOS");
                break;

            default:
                estadoPago.setNombre("ACEPT1000");
                estadoPago.setRazonEstado("PAGO ACEPTADO: PAGO PROCESADO CORRECTAMENTE");
                break;
        }

        return estadoPago;
    }

    @Transactional(readOnly = true)
    public List<PagoRecursoData> allPagos() {
        logger.debug("Devolviendo todos los pagos");

        // Obtener todos los pagos de la base de datos
        List<Pago> pagos = pagoRepository.findAll();

        // Convertir la lista de entidades a DTOs usando Java Stream API
        List<PagoRecursoData> pagoRecursoDataList = pagos.stream()
                .map(pago -> {
                    PagoRecursoData pagoRecursoData = modelMapper.map(pago, PagoRecursoData.class);

                    pagoRecursoData.setComercioData(modelMapper.map(pago.getComercio(), ComercioData.class));
                    pagoRecursoData.setEstadoPagoData(modelMapper.map(pago.getEstado(), EstadoPagoData.class));
                    pagoRecursoData.setTarjetaPagoData(modelMapper.map(pago.getTarjetaPago(), TarjetaPagoData.class));

                    return pagoRecursoData;
                })
                .sorted(Comparator.comparingLong(PagoRecursoData::getId)) // Ordenar por ID
                .collect(Collectors.toList());

        return pagoRecursoDataList;
    }

    @Transactional(readOnly = true)
    public List<PagoRecursoData> filtrarPagos(Long id,
                                              String ticket,
                                              String cif,
                                              String estado,
                                              Date fechaDesde,
                                              Date fechaHasta) {

        // 1) Obtenemos todos los pagos
        List<Pago> pagos = pagoRepository.findAll();

        // 2) Filtro en memoria por ID, ticket, cif, fechas
        Stream<Pago> streamPagos = pagos.stream();

        if (id != null) {
            streamPagos = streamPagos.filter(p -> p.getId().equals(id));
        }

        if (ticket != null && !ticket.trim().isEmpty()) {
            streamPagos = streamPagos.filter(p ->
                    p.getTicketExt() != null && p.getTicketExt().equalsIgnoreCase(ticket.trim())
            );
        }

        if (cif != null && !cif.trim().isEmpty()) {
            streamPagos = streamPagos.filter(p ->
                    p.getComercio().getCif() != null && p.getComercio().getCif().equalsIgnoreCase(cif.trim())
            );
        }

        if (fechaDesde != null) {
            streamPagos = streamPagos.filter(p -> !p.getFecha().before(fechaDesde));
        }

        if (fechaHasta != null) {
            streamPagos = streamPagos.filter(p -> !p.getFecha().after(fechaHasta));
        }

        // 3) Recogemos la lista filtrada
        List<Pago> pagosFiltrados = streamPagos.collect(Collectors.toList());

        // 4) Mapeamos cada pago a PagoRecursoData
        List<PagoRecursoData> resultado = pagosFiltrados.stream()
                .map(p -> {
                    PagoRecursoData prd = modelMapper.map(p, PagoRecursoData.class);
                    prd.setComercioData(modelMapper.map(p.getComercio(), ComercioData.class));
                    prd.setEstadoPagoData(modelMapper.map(p.getEstado(), EstadoPagoData.class));
                    prd.setTarjetaPagoData(modelMapper.map(p.getTarjetaPago(), TarjetaPagoData.class));
                    return prd;
                })
                .collect(Collectors.toList());

        // 5) Asignamos shownState ("Aceptado","Pendiente","Rechazado") según el estado real
        for (PagoRecursoData prd : resultado) {
            if (prd.getEstadoPagoData() != null && prd.getEstadoPagoData().getNombre() != null) {
                if (prd.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                    prd.setShownState("Aceptado");
                } else if (prd.getEstadoPagoData().getNombre().startsWith("PEND")) {
                    prd.setShownState("Pendiente");
                } else if (prd.getEstadoPagoData().getNombre().startsWith("RECH")) {
                    prd.setShownState("Rechazado");
                }
            }
        }

        // 6) Si el usuario seleccionó un estado (y no "Todos"), filtramos por shownState
        if (estado != null && !estado.trim().isEmpty() && !"Todos".equalsIgnoreCase(estado.trim())) {
            resultado = resultado.stream()
                    .filter(prd -> estado.equalsIgnoreCase(prd.getShownState()))
                    .collect(Collectors.toList());
        }

        // 7) Devolvemos la lista ordenada por ID
        return resultado.stream()
                .sorted(Comparator.comparingLong(PagoRecursoData::getId))
                .collect(Collectors.toList());
    }


    /**
     * Aplica los mismos filtros en memoria que filtrarPagos(...), pero además
     * añade paginación (4 elementos por página).
     *
     * @param page         Página actual (0-based).
     * @param size         Tamaño de página (4).
     * @param id           Filtro de ID (opcional).
     * @param ticket       Filtro de ticket (opcional).
     * @param cif          Filtro de cif (opcional).
     * @param estado       Filtro de estado (opcional, "Aceptado", "Pendiente", "Rechazado", "Todos").
     * @param fechaDesde   Fecha mínima (opcional).
     * @param fechaHasta   Fecha máxima (opcional).
     * @return Page<PagoRecursoData> con la sublista de elementos y la info de paginación.
     */
    @Transactional(readOnly = true)
    public Page<PagoRecursoData> filtrarPagosPaginado(int page,
                                                      int size,
                                                      Long id,
                                                      String ticket,
                                                      String cif,
                                                      String estado,
                                                      Date fechaDesde,
                                                      Date fechaHasta) {

        // 1) Usamos la lógica anterior de filtrarPagos(...) para no repetir
        List<PagoRecursoData> todosFiltrados = filtrarPagos(id, ticket, cif, estado, fechaDesde, fechaHasta);

        // 2) Ordenamos por ID de forma ascendente (ya lo hacía filtrarPagos, pero aseguramos)
        //    (Realmente filtrarPagos() ya lo ordena, pero lo dejamos por claridad.)
        List<PagoRecursoData> ordenados = new ArrayList<>(todosFiltrados);

        // 3) Paginamos manualmente.  (Podríamos usar una Query con paginación de JPA,
        //    pero aquí lo hacemos en memoria para no reescribir la lógica de filtrado.)
        int total = ordenados.size();
        int start = page * size;
        int end = Math.min(start + size, total);
        if (start > end) {
            // Si la página está fuera de rango, devolvemos una lista vacía
            List<PagoRecursoData> vacia = Collections.emptyList();
            return new PageImpl<>(vacia, PageRequest.of(page, size), total);
        }

        List<PagoRecursoData> subLista = ordenados.subList(start, end);

        return new PageImpl<>(subLista, PageRequest.of(page, size), total);
    }


    @Transactional(readOnly = true)
    public List<PagoRecursoData> filtrarPagosDeUnComercio(Long comercioId,
                                                          Long id,
                                                          String ticket,
                                                          String estado,
                                                          Date fechaDesde,
                                                          Date fechaHasta) {
        // 1) Obtenemos los pagos de ese comercio
        List<Pago> pagos = pagoRepository.findByComercioId(comercioId);

        // 2) Filtro en memoria por ID, ticket, fechas
        Stream<Pago> streamPagos = pagos.stream();

        if (id != null) {
            streamPagos = streamPagos.filter(p -> p.getId().equals(id));
        }

        if (ticket != null && !ticket.trim().isEmpty()) {
            streamPagos = streamPagos.filter(p ->
                    p.getTicketExt() != null && p.getTicketExt().equalsIgnoreCase(ticket.trim())
            );
        }

        if (fechaDesde != null) {
            streamPagos = streamPagos.filter(p -> !p.getFecha().before(fechaDesde));
        }

        if (fechaHasta != null) {
            streamPagos = streamPagos.filter(p -> !p.getFecha().after(fechaHasta));
        }

        // 3) Recogemos la lista filtrada
        List<Pago> pagosFiltrados = streamPagos.collect(Collectors.toList());

        // 4) Mapeamos cada pago a PagoRecursoData
        List<PagoRecursoData> resultado = pagosFiltrados.stream()
                .map(p -> {
                    PagoRecursoData prd = modelMapper.map(p, PagoRecursoData.class);
                    prd.setComercioData(modelMapper.map(p.getComercio(), ComercioData.class));
                    prd.setEstadoPagoData(modelMapper.map(p.getEstado(), EstadoPagoData.class));
                    prd.setTarjetaPagoData(modelMapper.map(p.getTarjetaPago(), TarjetaPagoData.class));
                    return prd;
                })
                .collect(Collectors.toList());

        // 5) Asignamos shownState ("Aceptado","Pendiente","Rechazado")
        for (PagoRecursoData prd : resultado) {
            if (prd.getEstadoPagoData() != null && prd.getEstadoPagoData().getNombre() != null) {
                if (prd.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                    prd.setShownState("Aceptado");
                } else if (prd.getEstadoPagoData().getNombre().startsWith("PEND")) {
                    prd.setShownState("Pendiente");
                } else if (prd.getEstadoPagoData().getNombre().startsWith("RECH")) {
                    prd.setShownState("Rechazado");
                }
            }
        }

        // 6) Si el usuario seleccionó un estado (y no "Todos"), filtramos por shownState
        if (estado != null && !estado.trim().isEmpty() && !"Todos".equalsIgnoreCase(estado.trim())) {
            resultado = resultado.stream()
                    .filter(prd -> estado.equalsIgnoreCase(prd.getShownState()))
                    .collect(Collectors.toList());
        }

        // 7) Devolvemos la lista ordenada por ID
        return resultado.stream()
                .sorted(Comparator.comparingLong(PagoRecursoData::getId))
                .collect(Collectors.toList());
    }

    /**
     * NUEVO: filtrarPagosDeUnComercioPaginado
     * Aplica la misma lógica de filtrarPagosDeUnComercio(...) pero con paginación.
     * Usamos 4 elementos por página.
     *
     * @param page       Página actual (0-based).
     * @param size       Tamaño de página (4).
     * @param comercioId ID del comercio.
     * @param id         Filtro de ID (opcional).
     * @param ticket     Filtro de ticket (opcional).
     * @param estado     Filtro de estado (opcional, "Aceptado", "Pendiente", "Rechazado", "Todos").
     * @param fechaDesde Fecha mínima (opcional).
     * @param fechaHasta Fecha máxima (opcional).
     * @return Page<PagoRecursoData> con la sublista de elementos y la info de paginación.
     */
    @Transactional(readOnly = true)
    public Page<PagoRecursoData> filtrarPagosDeUnComercioPaginado(int page,
                                                                  int size,
                                                                  Long comercioId,
                                                                  Long id,
                                                                  String ticket,
                                                                  String estado,
                                                                  Date fechaDesde,
                                                                  Date fechaHasta) {
        // 1) Obtenemos TODOS los pagos de ese comercio
        List<Pago> pagos = pagoRepository.findByComercioId(comercioId);

        // 2) Filtro en memoria por ID, ticket, fechas
        Stream<Pago> streamPagos = pagos.stream();

        if (id != null) {
            streamPagos = streamPagos.filter(p -> p.getId().equals(id));
        }

        if (ticket != null && !ticket.trim().isEmpty()) {
            streamPagos = streamPagos.filter(p ->
                    p.getTicketExt() != null && p.getTicketExt().equalsIgnoreCase(ticket.trim())
            );
        }

        if (fechaDesde != null) {
            streamPagos = streamPagos.filter(p -> !p.getFecha().before(fechaDesde));
        }

        if (fechaHasta != null) {
            streamPagos = streamPagos.filter(p -> !p.getFecha().after(fechaHasta));
        }

        // 3) Recogemos la lista filtrada
        List<Pago> pagosFiltrados = streamPagos.collect(Collectors.toList());

        // 4) Mapeamos cada pago a PagoRecursoData
        List<PagoRecursoData> resultado = pagosFiltrados.stream()
                .map(p -> {
                    PagoRecursoData prd = modelMapper.map(p, PagoRecursoData.class);
                    prd.setComercioData(modelMapper.map(p.getComercio(), ComercioData.class));
                    prd.setEstadoPagoData(modelMapper.map(p.getEstado(), EstadoPagoData.class));
                    prd.setTarjetaPagoData(modelMapper.map(p.getTarjetaPago(), TarjetaPagoData.class));
                    return prd;
                })
                .collect(Collectors.toList());

        // 5) Asignamos shownState ("Aceptado","Pendiente","Rechazado")
        for (PagoRecursoData prd : resultado) {
            if (prd.getEstadoPagoData() != null && prd.getEstadoPagoData().getNombre() != null) {
                if (prd.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                    prd.setShownState("Aceptado");
                } else if (prd.getEstadoPagoData().getNombre().startsWith("PEND")) {
                    prd.setShownState("Pendiente");
                } else if (prd.getEstadoPagoData().getNombre().startsWith("RECH")) {
                    prd.setShownState("Rechazado");
                }
            }
        }

        // 6) Si el usuario seleccionó un estado (y no "Todos"), filtramos por shownState
        if (estado != null && !estado.trim().isEmpty() && !"Todos".equalsIgnoreCase(estado.trim())) {
            resultado = resultado.stream()
                    .filter(prd -> estado.equalsIgnoreCase(prd.getShownState()))
                    .collect(Collectors.toList());
        }

        // 7) Ordenamos y paginamos manualmente en memoria
        resultado.sort(Comparator.comparingLong(PagoRecursoData::getId));

        int total = resultado.size();
        int start = page * size;
        int end = Math.min(start + size, total);

        if (start > end) {
            // Página fuera de rango
            List<PagoRecursoData> vacia = Collections.emptyList();
            return new PageImpl<>(vacia, PageRequest.of(page, size), total);
        }

        List<PagoRecursoData> subLista = resultado.subList(start, end);

        // Construimos la Page
        return new PageImpl<>(subLista, PageRequest.of(page, size), total);
    }

    @Transactional(readOnly = true)
    public PagoRecursoData obtenerPagoPorId(Long id) {
        // Buscar el Pago por ID
        Pago pago = pagoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Pago no encontrado para el ID proporcionado.")
        );

        // Mapeo detallado similar al de allPagos
        PagoRecursoData pagoRecursoData = modelMapper.map(pago, PagoRecursoData.class);

        // Mapeo de entidades relacionadas
        pagoRecursoData.setComercioData(modelMapper.map(pago.getComercio(), ComercioData.class));
        pagoRecursoData.setEstadoPagoData(modelMapper.map(pago.getEstado(), EstadoPagoData.class));
        pagoRecursoData.setTarjetaPagoData(modelMapper.map(pago.getTarjetaPago(), TarjetaPagoData.class));
        if(pago.getIncidencia()!= null){
            pagoRecursoData.setIncidenciaData(modelMapper.map(pago.getIncidencia(), IncidenciaData.class));
        }
        return pagoRecursoData;
    }


    @Transactional(readOnly = true)
    public List<PagoRecursoData> obtenerPagosDeUnComercio(Long comercioId) {
        logger.debug("Obteniendo pagos para el Comercio con ID: {}", comercioId);

        // Verificar si el Comercio existe
        Comercio comercio = comercioRepository.findById(comercioId).orElseThrow(() ->
                new IllegalArgumentException("Comercio no encontrado para el ID proporcionado.")
        );

        // Obtener los pagos asociados al Comercio
        List<Pago> pagos = pagoRepository.findByComercioId(comercioId);

        // Convertir la lista de entidades a DTOs usando ModelMapper
        List<PagoRecursoData> pagoRecursoDataList = pagos.stream()
                .map(pago -> {
                    PagoRecursoData pagoRecursoData = modelMapper.map(pago, PagoRecursoData.class);

                    pagoRecursoData.setComercioData(modelMapper.map(pago.getComercio(), ComercioData.class));
                    pagoRecursoData.setEstadoPagoData(modelMapper.map(pago.getEstado(), EstadoPagoData.class));
                    pagoRecursoData.setTarjetaPagoData(modelMapper.map(pago.getTarjetaPago(), TarjetaPagoData.class));

                    return pagoRecursoData;
                })
                .sorted(Comparator.comparingLong(PagoRecursoData::getId)) // Ordenar por ID
                .collect(Collectors.toList());

        return pagoRecursoDataList;
    }

    @Transactional(readOnly = true)
    public ComercioData obtenerComercioDeUsuarioLogeado(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado para el ID proporcionado: " + idUsuario));

        Comercio comercio = usuario.getComercio();
        if (comercio == null) {
            throw new IllegalArgumentException("El usuario no tiene un comercio asociado.");
        }

        ComercioData comercioData = modelMapper.map(comercio, ComercioData.class);
        return comercioData;
    }


}
