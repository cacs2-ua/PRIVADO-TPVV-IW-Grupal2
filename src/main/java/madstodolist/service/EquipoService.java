package madstodolist.service;

import madstodolist.dto.EquipoData;
import madstodolist.dto.TareaData;
import madstodolist.dto.UsuarioData;
import madstodolist.model.Equipo;
import madstodolist.model.Usuario;
import madstodolist.repository.EquipoRepository;
import madstodolist.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoService {
    Logger logger = LoggerFactory.getLogger(TareaService.class);

    @Autowired
    EquipoRepository equipoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public EquipoData crearEquipo(String nombre) {
        Equipo equipo = new Equipo(nombre);
        equipoRepository.save(equipo);
        return modelMapper.map(equipo, EquipoData.class);
    }

    @Transactional
    public EquipoData crearEquipo(String nombre, String tipo) {
        Equipo equipo = new Equipo(nombre);
        equipo.setTipo(tipo);
        equipoRepository.save(equipo);
        return modelMapper.map(equipo, EquipoData.class);
    }


    @Transactional(readOnly = true)
    public EquipoData recuperarEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + id + "no existe");
        }
        return modelMapper.map(equipo, EquipoData.class);
    }

    @Transactional(readOnly = true)
    public List<EquipoData> findAllOrdenadoPorNombre() {
        logger.debug("Devolviendo todos los equipos ordenados alfabéticamente ");

        // Convert each Equipo to EquipoData and collect as a list
        List<EquipoData> equipos = equipoRepository.findAll()
                .stream()
                .map(equipo -> modelMapper.map(equipo, EquipoData.class))
                .collect(Collectors.toList());

        // Sort the list by the team name
        equipos.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));

        return equipos;
    }


    @Transactional
    public void añadirUsuarioAEquipo(Long id, Long id1) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);

        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + id + "no existe");
        }

        Usuario usuario = usuarioRepository.findById(id1).orElse(null);

        if (usuario == null) {
            throw new EquipoServiceException("El usuario con id " + id + "no existe");
        }

        equipo.addUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioData> usuariosEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);

        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + id + "no existe");
        }

        // Hacemos uso de Java Stream API para mapear la lista de entidades a DTOs.
        return equipo.getUsuarios().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioData.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EquipoData> equiposUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            throw new EquipoServiceException("El usuario con id " + id + "no existe");
        }

        return  usuario.getEquipos().stream()
                .map(equipo -> modelMapper.map(equipo,EquipoData.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void borrarUsuarioDelEquipo(Long id, Long id1) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);

        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + id + "no existe");
        }

        Usuario usuario = usuarioRepository.findById(id1).orElse(null);

        if (usuario == null) {
            throw new EquipoServiceException("El usuario con id " + id + "no existe");
        }

        equipo.deleteUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public Boolean usuarioPerteneceAEquipo(Long idEquipo, Long idUsuario) {
        Equipo equipo = equipoRepository.findById(idEquipo).orElse(null);

        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + idEquipo + "no existe");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        if (usuario == null) {
            throw new EquipoServiceException("El usuario con id " + idUsuario + "no existe");
        }

        return  equipo.getUsuarios().contains(usuario);

    }

    @Transactional(readOnly = true)
    public List<Boolean> listaEquiposPerteneceUsuario(Long idUsuario) {

        List<EquipoData> equipos = findAllOrdenadoPorNombre();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        if (usuario == null) {
            throw new EquipoServiceException("El usuario con id " + idUsuario + "no existe");
        }

        List <Boolean> pertenecientes = equipos.stream()
                .map(equipo -> usuarioPerteneceAEquipo(equipo.getId(), idUsuario))
                .collect(Collectors.toList());

        return  pertenecientes;
    }

    public EquipoData modificarNombreEquipo(Long equipoId, String newName) {
        Equipo equipo = equipoRepository.findById(equipoId).orElse(null);
        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + equipoId + "no existe");
        }
        equipo.setNombre(newName);
        equipo = equipoRepository.save(equipo);
        return modelMapper.map(equipo, EquipoData.class);
    }

    public void borrarEquipo(Long equipoId) {
        logger.debug("Borrando el Equipo con id " + equipoId);
        Equipo equipo = equipoRepository.findById(equipoId).orElse(null);
        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + equipoId + "no existe");
        }
        equipoRepository.delete(equipo);
    }

    public EquipoData modificarTipoEquipo(Long equipoId, String tipo) {
        Equipo equipo = equipoRepository.findById(equipoId).orElse(null);
        if (equipo == null) {
            throw new EquipoServiceException("El equipo con id " + equipoId + " no existe");
        }
        equipo.setTipo(tipo);
        equipo = equipoRepository.save(equipo);
        return modelMapper.map(equipo, EquipoData.class);
    }

    public List<EquipoData> filtrarEquiposPorTipo(String tipo) {
        List<EquipoData> equipos = findAllOrdenadoPorNombre();
        return equipos.stream()
                .filter(equipo -> equipo.getTipo().equals(tipo))
                .collect(Collectors.toList());
    }
}
