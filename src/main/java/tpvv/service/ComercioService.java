package tpvv.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.dto.ComercioData;
import tpvv.dto.PersonaContactoData;
import tpvv.model.Comercio;
import tpvv.model.Pais;
import tpvv.model.PersonaContacto;
import tpvv.model.Usuario;
import tpvv.repository.ComercioRepository;
import org.modelmapper.ModelMapper;
import tpvv.repository.PaisRepository;
import tpvv.repository.PersonaContactoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import tpvv.repository.UsuarioRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.security.SecureRandom;

@Service
public class ComercioService {
    @Autowired
    ComercioRepository comercioRepository;

    @Autowired
    PaisRepository paisRepository;


    @Autowired
    private ModelMapper modelMapper;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    @Autowired
    private PersonaContactoRepository personaContactoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private String generateApiKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        String apiKey;
        do {
            StringBuilder apiKeyBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = RANDOM.nextInt(CHARACTERS.length());
                apiKeyBuilder.append(CHARACTERS.charAt(index));
            }
            apiKey = apiKeyBuilder.toString();
        } while (isApiKeyDuplicate(apiKey));

        return apiKey;
    }

    private boolean isApiKeyDuplicate(String apiKey) {
        try {
            obtenerComercioPorApiKey(apiKey);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    private void checkCampos(ComercioData comercio){
        if (comercio.getNombre() == null)
            throw new ComercioServiceException("El nombre no puede ser nulo");
        else if (comercio.getCif() == null)
            throw new ComercioServiceException("El cif no puede ser nulo");
        else if (comercio.getPais() == null)
            throw new ComercioServiceException("El país no puede ser nulo");
        else if (comercio.getProvincia() == null)
            throw new ComercioServiceException("La provincia no puede ser nula");
        else if (comercio.getDireccion() == null)
            throw new ComercioServiceException("La dirección no puede ser nula");
        else if (comercio.getIban() == null)
            throw new ComercioServiceException("El IBAN no puede ser nulo");
        else if (comercio.getUrl_back() == null)
            throw new ComercioServiceException("La URL no puede ser nula");
    }

    @Transactional
    public ComercioData crearComercio(ComercioData comercio) {
        Pais pais_id = paisRepository.findByNombre(comercio.getPais())
                .orElseThrow(() -> new ComercioServiceException("El país especificado no existe"));

        try{
            checkCampos(comercio);
            comercio.setApiKey(generateApiKey(32));
            Comercio comercioNuevo = modelMapper.map(comercio, Comercio.class);
            pais_id.addComercio(comercioNuevo);
            comercioRepository.save(comercioNuevo);
            return modelMapper.map(comercioNuevo, ComercioData.class);
        }
        catch (Exception e){
            throw new ComercioServiceException(e.getMessage());
        }

    }

    @Transactional
    public PersonaContactoData crearPersonaContacto(PersonaContactoData personaContacto) {
        if (personaContacto.getNombreContacto() == null)
            throw new ComercioServiceException("El nombre no puede ser nulo");
        else if (personaContacto.getEmail() == null)
            throw new ComercioServiceException("El email no puede ser nulo");
        else if (personaContacto.getTelefono() == null)
            throw new ComercioServiceException("El teléfono no puede ser nulo");
        else{
            PersonaContacto personaContactoNueva = modelMapper.map(personaContacto, PersonaContacto.class);
            personaContactoRepository.save(personaContactoNueva);
            return modelMapper.map(personaContactoNueva, PersonaContactoData.class);
        }

    }

    @Transactional
    public void asignarPersonaDeContactoAComercio(Long idComercio, Long idPersona) {
        Comercio comercio = comercioRepository.findById(idComercio)
                .orElseThrow(() -> new ComercioServiceException("Comercio no encontrado con ID: " + idComercio));
        PersonaContacto persona = personaContactoRepository.findById(idPersona)
                .orElseThrow(() -> new ComercioServiceException("Persona de contacto no encontrada con ID: " + idComercio));
        comercio.setPersonaContacto(persona);
    }

    @Transactional(readOnly = true)
    public PersonaContactoData recuperarPersonaContactoById(Long id) {
        PersonaContacto persona = personaContactoRepository.findById(id).orElse(null);
        if (persona == null){
            throw new ComercioServiceException("La persona de contacto " + id + " no existe");
        }
        return modelMapper.map(persona, PersonaContactoData.class);
    }

    @Transactional(readOnly = true)
    public PersonaContactoData recuperarPersonaContactoByComercioId(Long id) {
        Comercio comercio = comercioRepository.findById(id).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + id + " no existe");
        }
        PersonaContacto persona = comercio.getPersonaContacto();
        if (persona == null) return null;
        return modelMapper.map(persona, PersonaContactoData.class);
    }

    @Transactional(readOnly = true)
    public ComercioData recuperarComercio(Long id) {
        Comercio comercio = comercioRepository.findById(id).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + id + " no existe");
        }
        return modelMapper.map(comercio, ComercioData.class);
    }

    @Transactional(readOnly = true)
    public List<ComercioData> recuperarTodosLosComercios() {
        List<Comercio> comercios = comercioRepository.findAll();
        return comercios.stream()
                .filter(comercio -> comercio.getId() != 3)
                .sorted(Comparator.comparingLong(Comercio::getId)) // Ordena por id
                .map(comercio -> modelMapper.map(comercio, ComercioData.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ComercioData> recuperarComerciosPaginados(List<ComercioData> comerciosData, int page, int size) {
        if (comerciosData == null || comerciosData.isEmpty()) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), 0);
        }
        int start = page * size;
        int end = Math.min(start + size, comerciosData.size());

        List<ComercioData> comerciosPaginados = comerciosData.subList(start, end);
        return new PageImpl<>(comerciosPaginados, PageRequest.of(page, size), comerciosData.size());
    }

    @Transactional
    public void actualizarURLComercio(Long id, String url_back) {
        Comercio comercio = comercioRepository.findById(id).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + id + " no existe");
        }
        comercio.setUrl_back(url_back);

    }
    @Transactional
    public void regenerarAPIKeyComercio(Long id) {
        Comercio comercio = comercioRepository.findById(id).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + id + " no existe");
        }
        String nuevaApiKey = generateApiKey(32);
        comercio.setApiKey(nuevaApiKey);
    }

    @Transactional
    public void modificarEstadoComercio(Long id, boolean activado) {
        Comercio comercio = comercioRepository.findById(id).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + id + " no existe");
        }
        List<Usuario> usuariosComercio = usuarioRepository.findByComercio(comercio);
        if (activado == false) {
            for (Usuario usuario : usuariosComercio){
                usuario.setActivo(activado);
            }
        }

        comercio.setActivo(activado);
    }

    @Transactional(readOnly = true)
    public List<ComercioData> filtrarComercios(
            List<ComercioData> comercios,
            Long id, String nombre, String cif, String pais,
            LocalDate fechaDesde, LocalDate fechaHasta) {

        final String paisFiltrado = (pais != null && pais.isEmpty()) ? null : pais;

        return comercios.stream()
                .filter(comercio -> id == null || comercio.getId().equals(id))
                .filter(comercio -> nombre == null || comercio.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(comercio -> cif == null || comercio.getCif().toLowerCase().contains(cif.toLowerCase()))
                .filter(comercio -> paisFiltrado == null || comercio.getPais().equalsIgnoreCase(paisFiltrado))
                .filter(comercio -> fechaDesde == null || !comercio.getFechaAlta().before(Timestamp.valueOf(fechaDesde.atStartOfDay())))
                .filter(comercio -> fechaHasta == null || !comercio.getFechaAlta().after(Timestamp.valueOf(fechaHasta.atStartOfDay())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComercioData obtenerComercioPorApiKey(String apiKey) {
        return comercioRepository.findByApiKey(apiKey)
                .map(comercio -> modelMapper.map(comercio, ComercioData.class))
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un comercio con la API key proporcionada: " + apiKey));
    }



}
