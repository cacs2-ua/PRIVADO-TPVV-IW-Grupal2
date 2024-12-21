package tpvv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.dto.ComercioData;
import tpvv.model.Comercio;
import tpvv.model.Pais;
import tpvv.repository.ComercioRepository;
import org.modelmapper.ModelMapper;
import tpvv.repository.PaisRepository;

@Service
public class ComercioService {
    @Autowired
    ComercioRepository comercioRepository;

    @Autowired
    PaisRepository paisRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ComercioData crearComercio(String nombre, String cif, String pais, String provincia, String direccion,
                                      String iban, String apiKey, String url_back) {
        if (nombre == ""){
            throw new ComercioServiceException("El nombre del comercio está vacío");
        }
        Pais pais_id = paisRepository.findByNombre(pais)
                .orElseThrow(() -> new ComercioServiceException("El país especificado no existe"));

        Comercio comercio = new Comercio(nombre, cif, pais, provincia, direccion, iban, apiKey, url_back);
        pais_id.addComercio(comercio);
        comercioRepository.save(comercio);
        return modelMapper.map(comercio, ComercioData.class);
    }

    @Transactional(readOnly = true)
    public ComercioData recuperarComercio(Long id) {
        Comercio comercio = comercioRepository.findById(id).orElse(null);
        if (comercio == null){
            throw new ComercioServiceException("El comercio " + id + " no existe");
        }
        return modelMapper.map(comercio, ComercioData.class);
    }
}
