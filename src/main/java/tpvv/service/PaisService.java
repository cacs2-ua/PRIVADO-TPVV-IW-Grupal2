package tpvv.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpvv.dto.PaisData;
import tpvv.dto.UsuarioData;
import tpvv.model.Pais;
import tpvv.model.Usuario;
import tpvv.repository.PaisRepository;
import tpvv.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<PaisData> findAll() {
        List<Pais> paises = paisRepository.findAll();
        return paises.stream()
                .map(pais -> modelMapper.map(pais, PaisData.class))
                .collect(Collectors.toList());

    }
}
