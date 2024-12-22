package tpvv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.dto.ComercioData;
import tpvv.dto.PersonaContactoData;
import tpvv.model.Comercio;
import tpvv.model.Pais;
import tpvv.model.PersonaContacto;
import tpvv.repository.ComercioRepository;
import org.modelmapper.ModelMapper;
import tpvv.repository.PaisRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

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

    private String generateApiKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        StringBuilder apiKey = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            apiKey.append(CHARACTERS.charAt(index));
        }
        return apiKey.toString();
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
                .map(comercio -> modelMapper.map(comercio, ComercioData.class))
                .collect(Collectors.toList());
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
}
