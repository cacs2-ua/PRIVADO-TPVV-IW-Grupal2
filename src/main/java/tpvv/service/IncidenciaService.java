package tpvv.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.dto.*;
import tpvv.model.*;
import tpvv.model.ValoracionTecnico;
import tpvv.repository.*;
import tpvv.service.exception.IncidenciaServiceException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EstadoIncidenciaRepository estadoIncidenciaRepository;
    @Autowired
    private MensajeRepository mensajeRepository;

    private static final int USUARIO_TECNICO = 2;
    private static final int USUARIO_COMERCIO = 3;
    private static final String ESTADO_NUEVA = "NUEVA";
    private static final String ESTADO_ASIGNADA = "ASIGN";
    private static final String ESTADO_RESUELTA = "RESUELTA";
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private ValoracionTecnicoRepository valoracionTecnicoRepository;

    private void checkCampos(IncidenciaData incidencia) {

        if(incidencia.getTitulo() == null  || incidencia.getTitulo().isEmpty()) {
            throw new IncidenciaServiceException("El título no puede estar vacio o ser nulo");
        }

        if(incidencia.getDescripcion() == null  || incidencia.getDescripcion().isEmpty()) {
            throw new IncidenciaServiceException("El título no puede estar vacio o ser nulo");
        }

    }
    @Transactional
    public IncidenciaData createIncidencia(IncidenciaData incidencia) {

        Usuario usuarioInci = usuarioRepository.getReferenceById(incidencia.getUsuarioComercio().getId());
        EstadoIncidencia estado = estadoIncidenciaRepository.findByNombre(ESTADO_NUEVA).
                orElseThrow(() -> new IncidenciaServiceException("El estado no existe"));
        try{
            checkCampos(incidencia);
            Incidencia incidenciaNueva = modelMapper.map(incidencia, Incidencia.class);
            incidenciaNueva.setUsuarioComercio(usuarioInci);
            incidenciaNueva.setEstado(estado);
            incidenciaNueva.setFecha(new Date());

            if(incidencia.getPago_id() != null) {
                Pago pago = pagoRepository.getReferenceById(incidencia.getPago_id());
                incidenciaNueva.setPago(pago);
            }

            incidenciaRepository.save(incidenciaNueva);
            return modelMapper.map(incidenciaNueva, IncidenciaData.class);
        }
        catch (Exception e){

            throw new IncidenciaServiceException(e.getMessage());
        }
    }

    @Transactional
    public boolean asignarIncidencia(Long idIncidencia, Long idUsuario) {

        Usuario usuario = usuarioRepository.getReferenceById(idUsuario);

        Incidencia incidencia = incidenciaRepository.getReferenceById(idIncidencia);
            if(incidencia.getUsuarioTecnico() == null && usuario.getTipo().getId() == USUARIO_TECNICO) {
                EstadoIncidencia estado = estadoIncidenciaRepository.findByNombre(ESTADO_ASIGNADA).
                        orElseThrow(() -> new IncidenciaServiceException("El estado no existe"));
                incidencia.setUsuarioTecnico(usuario);

                incidencia.setEstado(estado);
                incidenciaRepository.save(incidencia);
                return true;

        }
        return false;
    }

    @Transactional
    public boolean addMensaje(Long idIncidencia,String mensaje, Long idUsuario) {

        Usuario usuario = usuarioRepository.getReferenceById(idUsuario);
        Incidencia incidencia = incidenciaRepository.getReferenceById(idIncidencia);
        if(incidencia.getUsuarioTecnico() == usuario || incidencia.getUsuarioComercio() == usuario) {
            Mensaje mensajeEnt = new Mensaje(mensaje, usuario);
            mensajeEnt.setIncidencia(incidencia);
            mensajeEnt.setFecha(new Date());
            mensajeRepository.save(mensajeEnt);
            return true;

        }
        return false;
    }

    @Transactional
    public boolean addValoracion(Long idIncidencia,int valoracion,String mensaje, Long idUsuario) {

        Usuario usuario = usuarioRepository.getReferenceById(idUsuario);
        Incidencia incidencia = incidenciaRepository.getReferenceById(idIncidencia);
        if(valoracion <= 0 ||  incidencia.getValoracion() != 0 || incidencia.getUsuarioTecnico() == null ||
                !Objects.equals(incidencia.getUsuarioComercio().getId(), usuario.getId())) {
            return false;
        }
        if(incidencia.getEstado().getNombre().equals(ESTADO_RESUELTA) || incidencia.getUsuarioTecnico() != null) {
            ValoracionTecnico valoracionTecnico = new ValoracionTecnico(valoracion, incidencia.getUsuarioTecnico());
            incidencia.setValoracion(valoracion);
            incidencia.setRazon_valoracion(mensaje);
            valoracionTecnicoRepository.save(valoracionTecnico);
            incidenciaRepository.save(incidencia);
            return true;

        }
        return false;
    }

    @Transactional
    public boolean cambiarEstado(Long idIncidencia,boolean cerrar, Long idUsuario) {

        Usuario usuario = usuarioRepository.getReferenceById(idUsuario);
        Incidencia incidencia = incidenciaRepository.getReferenceById(idIncidencia);
        if(incidencia.getUsuarioTecnico() == usuario){
                EstadoIncidencia estado;
                if(cerrar && incidencia.getEstado().getNombre().equals(ESTADO_ASIGNADA)) {
                    estado = estadoIncidenciaRepository.findByNombre(ESTADO_RESUELTA).
                            orElseThrow(() -> new IncidenciaServiceException("El estado no existe"));
                }else{
                    estado = estadoIncidenciaRepository.findByNombre(ESTADO_ASIGNADA).
                            orElseThrow(() -> new IncidenciaServiceException("El estado no existe"));
                }
                incidencia.setEstado(estado);
                incidenciaRepository.save(incidencia);
                return true;

        }
        return false;
    }
    @Transactional(readOnly = true)
    public boolean canAddMessage(IncidenciaData incidencia, UsuarioData usuario) {
        Date fecha = new Date();
        boolean canAdd = false;
        if(!incidencia.getEstado().getNombre().equals(ESTADO_ASIGNADA) ||
                (!(Objects.equals(incidencia.getUsuarioTecnico().getId(), usuario.getId())) &&
                        !(Objects.equals(incidencia.getUsuarioComercio().getId(), usuario.getId())))){
            return canAdd;
        }
        if(incidencia.getMensajes().isEmpty()){
            if(usuario.getTipoId() == USUARIO_TECNICO &&
                    incidencia.getUsuarioTecnico() != null &&
                    Objects.equals(usuario.getTipoId(), incidencia.getUsuarioTecnico().getTipoId())) {
                canAdd = true;
            }

        }else{
            MensajeData mensaje = new ArrayList<>(incidencia.getMensajes()).get(incidencia.getMensajes().size()-1);
            if(!Objects.equals(mensaje.getUsuario().getId(), usuario.getId())){
                canAdd = true;
            }
        }
        return canAdd;
    }


    private List<IncidenciaData> filtrarIncidencias(Long id,
                                              Long usuarioId,
                                              boolean asignado,
                                              String cif,
                                              String estado,
                                              Date fechaDesde,
                                              Date fechaHasta) {

        Usuario usuarioInci = usuarioRepository.getReferenceById(usuarioId);
        List<Incidencia> incidencias;
        if(usuarioInci.getTipo().getId() == USUARIO_COMERCIO) {
            incidencias = incidenciaRepository.findIncidenciasByUsuarioComercio_Id(usuarioId);
        }
        else{
            incidencias = incidenciaRepository.findAll();
        }


        // 2) Filtro en memoria por ID, ticket, cif, fechas
        Stream<Incidencia> streamIncidencias = incidencias.stream();

        if (id != null) {
            streamIncidencias = streamIncidencias.filter(p -> p.getId().equals(id));
        }

        if (asignado && usuarioId != null && usuarioId > 0) {
            streamIncidencias = streamIncidencias.filter(i ->
                    i.getUsuarioTecnico() != null &&
                    Objects.equals(i.getUsuarioTecnico().getId(), usuarioId)
            );
        }

        if (cif != null && !cif.trim().isEmpty()) {
            streamIncidencias = streamIncidencias.filter(i ->
                    i.getUsuarioComercio().getComercio().getCif() != null && i.getUsuarioComercio().getComercio().getCif().equalsIgnoreCase(cif.trim())
            );
        }

        if (fechaDesde != null) {
            streamIncidencias = streamIncidencias.filter(i -> !i.getFecha().before(fechaDesde));
        }

        if (fechaHasta != null) {
            streamIncidencias = streamIncidencias.filter(i -> !i.getFecha().after(fechaHasta));
        }

        // 3) Recogemos la lista filtrada
        List<Incidencia> incidenciasFiltradas = streamIncidencias.collect(Collectors.toList());

        // 4) Mapeamos cada pago a PagoRecursoData
        List<IncidenciaData> resultado = incidenciasFiltradas.stream()
                .map(incidencia -> {
                    IncidenciaData incidenciaData = modelMapper.map(incidencia, IncidenciaData.class);

                    incidenciaData.setUsuarioComercio(modelMapper.map(incidencia.getUsuarioComercio(), UsuarioData.class));
                    if(incidencia.getUsuarioTecnico() != null) {
                        incidenciaData.setUsuarioTecnico(modelMapper.map(incidencia.getUsuarioTecnico(), UsuarioData.class));
                    }
                    if(incidencia.getPago() != null) {
                        incidenciaData.setPago_id(incidencia.getPago().getId());
                    }
                    incidenciaData.setEstado(modelMapper.map(incidencia.getEstado(),EstadoIncidenciaData.class));

                    return incidenciaData;
                })
                .collect(Collectors.toList());



        // 6) Si el usuario seleccionó un estado (y no "Todos"), filtramos por shownState
        if (estado != null && !estado.trim().isEmpty() && !"Todos".equalsIgnoreCase(estado.trim())) {
            resultado = resultado.stream()
                    .filter(prd -> estado.equalsIgnoreCase(prd.getEstado().getNombre()))
                    .collect(Collectors.toList());
        }

        // 7) Devolvemos la lista ordenada por ID
        return resultado.stream()
                .sorted(Comparator.comparingLong(IncidenciaData::getId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<IncidenciaData> filtrarIncidencias(int page,
                                                      int size,
                                                      Long id,
                                                   Long usuarioId,
                                                   boolean asignado,
                                                   String cif,
                                                      String estado,
                                                      Date fechaDesde,
                                                      Date fechaHasta) {

        // 1) Usamos la lógica anterior de filtrarPagos(...) para no repetir
        List<IncidenciaData> todosFiltrados = filtrarIncidencias(id, usuarioId,asignado, cif, estado, fechaDesde, fechaHasta);

        // 2) Ordenamos por ID de forma ascendente (ya lo hacía filtrarPagos, pero aseguramos)
        //    (Realmente filtrarPagos() ya lo ordena, pero lo dejamos por claridad.)
        List<IncidenciaData> ordenados = new ArrayList<>(todosFiltrados);

        // 3) Paginamos manualmente.  (Podríamos usar una Query con paginación de JPA,
        //    pero aquí lo hacemos en memoria para no reescribir la lógica de filtrado.)
        int total = ordenados.size();
        int start = page * size;
        int end = Math.min(start + size, total);
        if (start > end) {
            // Si la página está fuera de rango, devolvemos una lista vacía
            List<IncidenciaData> vacia = Collections.emptyList();
            return new PageImpl<>(vacia, PageRequest.of(page, size), total);
        }

        List<IncidenciaData> subLista = ordenados.subList(start, end);

        return new PageImpl<>(subLista, PageRequest.of(page, size), total);
    }

    @Transactional(readOnly = true)
    public IncidenciaData obtenerIncidencia(Long id) {
        Incidencia incidencia = incidenciaRepository.findIncidenciaById(id).orElseThrow(() ->
                new IllegalArgumentException("Incidencia no encontrada para el ID proporcionado.")
        );

        IncidenciaData incidenciaData = modelMapper.map(incidencia, IncidenciaData.class);

        // Mapeo de entidades relacionadas
        incidenciaData.setUsuarioComercio(modelMapper.map(incidencia.getUsuarioComercio(), UsuarioData.class));
        if(incidencia.getUsuarioTecnico() != null) {
            incidenciaData.setUsuarioTecnico(modelMapper.map(incidencia.getUsuarioTecnico(), UsuarioData.class));
        }
        if(incidencia.getPago() != null) {
            incidenciaData.setPago_id(incidencia.getPago().getId());
        }
        incidenciaData.setEstado(modelMapper.map(incidencia.getEstado(),EstadoIncidenciaData.class));

        for (Mensaje mensaje : incidencia.getMensajes()) {
            incidenciaData.setMensajes(modelMapper.map(mensaje, MensajeData.class));
        }

        return incidenciaData;
    }


}
