package tpvv.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpvv.dto.PagoCompletoRequest;
import tpvv.dto.PagoData;
import tpvv.dto.PedidoCompletoRequest;
import tpvv.dto.TarjetaPagoData;
import tpvv.model.Comercio;
import tpvv.model.EstadoPago;
import tpvv.model.Pago;
import tpvv.model.TarjetaPago;
import tpvv.repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarjetaPagoRepository tarjetaPagoRepository;

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    @Transactional
    public String obtenerUrlBack(String apiKey) {
        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
        }
        return comercio.getUrl_back();
    }

    @Transactional
    public String obtenerNombreComercio(String apiKey) {
        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
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
        pago.setFecha(fechaDate);
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
}
