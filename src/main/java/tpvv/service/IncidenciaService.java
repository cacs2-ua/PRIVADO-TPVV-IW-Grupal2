package tpvv.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.dto.*;
import tpvv.model.EstadoIncidencia;
import tpvv.model.Incidencia;
import tpvv.model.Usuario;
import tpvv.repository.EstadoIncidenciaRepository;
import tpvv.repository.IncidenciaRepository;
import tpvv.repository.UsuarioRepository;
import tpvv.service.exception.IncidenciaServiceException;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        EstadoIncidencia estado = estadoIncidenciaRepository.findByNombre("NUEVA").
                orElseThrow(() -> new IncidenciaServiceException("El estado no existe"));
        ;
        try{
            checkCampos(incidencia);
            Incidencia incidenciaNueva = modelMapper.map(incidencia, Incidencia.class);
            incidenciaNueva.setUsuarioComercio(usuarioInci);
            incidenciaNueva.setEstado(estado);
            incidenciaNueva.setFecha(new Date());

            incidenciaRepository.save(incidenciaNueva);
            return modelMapper.map(incidenciaNueva, IncidenciaData.class);
        }
        catch (Exception e){
            throw new IncidenciaServiceException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<IncidenciaData> obtenerIncidenciasUsuario(Long idUsuario) {

        List<Incidencia> incidencias = incidenciaRepository.findIncidenciasByUsuarioComercio_Id(idUsuario);

        List<IncidenciaData> incidenciaDataList = incidencias.stream()
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
                .sorted(Comparator.comparingLong(IncidenciaData::getId)) // Ordenar por ID
                .collect(Collectors.toList());
        return incidenciaDataList;
    }


}
