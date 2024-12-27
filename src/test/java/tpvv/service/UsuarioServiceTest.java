package tpvv.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import tpvv.dto.ComercioData;
import tpvv.dto.RegistroData;
import tpvv.dto.UsuarioData;
import tpvv.model.Pais;
import tpvv.model.TipoUsuario;
import tpvv.repository.PaisRepository;
import tpvv.repository.TipoUsuarioRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/sql/clean-test-db.sql")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    ComercioService comercioService;

    @Autowired
    PaisRepository paisRepository;


    private ComercioData crearComercio() {
        List<ComercioData> comercios = comercioService.recuperarTodosLosComercios();
        if (comercios.size() > 0){
            return comercios.get(0);
        }
        ComercioData nuevoComercio = new ComercioData();
        Pais nuevoPais = new Pais("España");

        if (paisRepository.findByNombre("España").isEmpty()) {
            paisRepository.save(nuevoPais);
        }

        nuevoComercio.setNombre("Comercio Test");
        nuevoComercio.setCif("A12345678");
        nuevoComercio.setPais("España");
        nuevoComercio.setProvincia("Madrid");
        nuevoComercio.setDireccion("Calle Falsa 123");
        nuevoComercio.setIban("ES6621000418401234567891");
        nuevoComercio.setUrl_back("http://callback.test");


        ComercioData comercio = comercioService.crearComercio(nuevoComercio);
        return comercio;
    }

    private UsuarioData crearUsuario() {
        crearComercio();
        RegistroData registro = new RegistroData();
        TipoUsuario nuevoTipo = new TipoUsuario("Comercio");

        if (tipoUsuarioRepository.findByNombre("Comercio").isEmpty()) {
            tipoUsuarioRepository.save(nuevoTipo);
        }
        Long idTipo = tipoUsuarioRepository.findByNombre("Comercio").get().getId();

        registro.setNombre("Usuario Test");
        registro.setContrasenya("A12345678");
        registro.setEmail("test@test.com");
        registro.setTipoId(idTipo);


        UsuarioData Usuario = usuarioService.registrar(registro);
        return Usuario;
    }

    @Test
    public void crearRecuperarUsuarioTest() {
        UsuarioData usuario = crearUsuario();
        assertThat(usuario.getId()).isNotNull();

        List<UsuarioData> usuarioBd = usuarioService.findAll();
        assertThat(usuarioBd).isNotNull();
        assertThat(usuarioBd.get(0).getNombre()).isEqualTo("Usuario Test");
        assertThat((usuarioBd).size()>0).isTrue();
    }
}
