package tpvv.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tpvv.dto.ComercioData;
import tpvv.dto.RegistroData;
import tpvv.dto.UsuarioData;
import tpvv.model.Comercio;
import tpvv.model.TipoUsuario;
import tpvv.model.Usuario;
import tpvv.model.ValoracionTecnico;
import tpvv.repository.ComercioRepository;
import tpvv.repository.TipoUsuarioRepository;
import tpvv.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.repository.ValoracionTecnicoRepository;
import tpvv.service.exception.UsuarioServiceException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private ComercioService comercioService;
    @Autowired
    private ValoracionTecnicoRepository valoracionTecnicoRepository;

    public enum LoginStatus {LOGIN_OK, USER_NOT_FOUND,USER_DISABLED, ERROR_PASSWORD}

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Repositorio para tipos de usuario
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    // Repositorio para comercio, si quieres buscar uno existente por ID
    // @Autowired
    // private ComercioRepository comercioRepository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public LoginStatus login(String eMail, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(eMail);
        if (!usuario.isPresent()) {
            return LoginStatus.USER_NOT_FOUND;
        } else if (!encoder.matches(password, usuario.get().getContrasenya())) {
            return LoginStatus.ERROR_PASSWORD;
        }
          else if (usuario.get().getActivo() == false) {
            return LoginStatus.USER_DISABLED;
        }
        else {
            return LoginStatus.LOGIN_OK;
        }
    }

    @Transactional
    public UsuarioData registrar(RegistroData registroData) {
        // Validaciones básicas
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(registroData.getEmail());
        if (usuarioBD.isPresent())
            throw new UsuarioServiceException("El usuario " + registroData.getEmail() + " ya está registrado");
        else if (registroData.getEmail() == null)
            throw new UsuarioServiceException("El usuario no tiene email");
        else if (registroData.getContrasenya() == null)
            throw new UsuarioServiceException("El usuario no tiene password");
        else if (registroData.getComercioId() == null)
            throw new UsuarioServiceException("El usuario no tiene comercio asignado");

        // Encriptar contraseña
        String contraEnClaro = registroData.getContrasenya();
        registroData.setContrasenya(encoder.encode(contraEnClaro));

        // Convertir DTO -> Entidad
        Usuario usuarioNuevo = modelMapper.map(registroData, Usuario.class);

        // Asignar TipoUsuario
        if (registroData.getTipoId() == null) {
            throw new UsuarioServiceException("No se especificó el tipo de usuario");
        }

        TipoUsuario tipo = tipoUsuarioRepository.findById(registroData.getTipoId())
                .orElseThrow(() -> new UsuarioServiceException("Tipo de usuario inválido"));
        usuarioNuevo.setTipo(tipo);
        Comercio comercioDefecto;

            /*comercioDefecto = comercioRepository.findById(1L)
                    .orElseThrow(() -> new UsuarioServiceException("No existe comercio con ID=1 en la base de datos"));*/

            comercioDefecto = comercioRepository.findById(registroData.getComercioId())
                    .orElseThrow(() -> new UsuarioServiceException("No existe comercio con ID=" + registroData.getComercioId() + " en la base de datos"));


        // Asignarlo al usuario para no violar la constraint
        usuarioNuevo.setComercio(comercioDefecto);

        usuarioNuevo = usuarioRepository.save(usuarioNuevo);
        return modelMapper.map(usuarioNuevo, UsuarioData.class);
    }

    @Transactional(readOnly = true)
    public UsuarioData findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    @Transactional(readOnly = true)
    public UsuarioData findById(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }



    @Transactional(readOnly = true)
    public List<UsuarioData> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .filter(usuario -> usuario.getTipo().getId() != 1)
                .sorted(Comparator.comparing(Usuario::getId))
                .map(usuario -> modelMapper.map(usuario, UsuarioData.class))
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public Long findComercio(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return null;
        return usuario.getComercio().getId();
    }

    @Transactional(readOnly = true)
    public int getValoracionUsuario(Long usuarioId) {
        List<ValoracionTecnico> valoraciones = valoracionTecnicoRepository.findValoracionTecnicosByTecnicoId(usuarioId);
        int numValoraciones= 0;
        int totalValoraciones = 0;
        for (ValoracionTecnico valoracion : valoraciones) {
            totalValoraciones += valoracion.getValoracion();
            numValoraciones++;
        }
        int resultado = numValoraciones >0 ? totalValoraciones / numValoraciones : 0;
        return resultado;
    }

    @Transactional(readOnly = true)
    public ComercioData obtenerComercio(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(Usuario::getComercio)
                .map(comercio -> modelMapper.map(comercio, ComercioData.class))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<UsuarioData> findAllByIdComercio(Long idComercio) {
        Comercio comercio = comercioRepository.findById(idComercio).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + idComercio + " no existe");
        }

        List<Usuario> usuarios = usuarioRepository.findByComercio(comercio);

        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioData.class))
                .collect(Collectors.toList());

    }

    @Transactional
    public void borradoUsuarioLogico(Long id, boolean activo){ // el argumento activo ayuda a reutilizar el metodo para restaurar los usuarios eliminados
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null){
            throw new UsuarioServiceException("El usuario " + id + " no existe");
        }
        usuario.setActivo(activo);

    }

    @Transactional(readOnly = true)
    public Page<UsuarioData> recuperarUsuariosPaginados(List<UsuarioData> usuariosData, int page, int size) {
        if (usuariosData == null || usuariosData.isEmpty()) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), 0);
        }
        int start = page * size;
        int end = Math.min(start + size, usuariosData.size());

        List<UsuarioData> usuariosPaginados = usuariosData.subList(start, end);
        return new PageImpl<>(usuariosPaginados, PageRequest.of(page, size), usuariosData.size());
    }

    @Transactional(readOnly = true)
    public List<UsuarioData> filtrarUsuarios(
            List<UsuarioData> usuarios,
            Long id, Long comercio, Boolean estado,
            LocalDate fechaDesde, LocalDate fechaHasta) {

        return usuarios.stream()
                .filter(usuario -> id == null || usuario.getId().equals(id))
                .filter(usuario -> comercio == null || usuario.getComercio().getId().equals(comercio))
                .filter(usuario -> estado == null || usuario.getActivo() == estado)
                .filter(usuario -> fechaDesde == null || !usuario.getFechaAlta().before(Timestamp.valueOf(fechaDesde.atStartOfDay())))
                .filter(usuario -> fechaHasta == null || !usuario.getFechaAlta().after(Timestamp.valueOf(fechaHasta.atStartOfDay())))
                .collect(Collectors.toList());
    }


}

