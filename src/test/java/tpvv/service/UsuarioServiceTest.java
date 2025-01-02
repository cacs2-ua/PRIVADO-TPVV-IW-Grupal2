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
        ComercioData comercio = crearComercio();
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
        registro.setComercioId(comercio.getId());


        UsuarioData Usuario = usuarioService.registrar(registro);

        registro.setEmail("eee@ee");
        usuarioService.registrar(registro);

        return Usuario;
    }

    @Test
    public void crearRecuperarUsuarioTest() {
        UsuarioData usuario = crearUsuario();
        Long idComercio = usuarioService.findComercio(usuario.getId());
        assertThat(usuario.getId()).isNotNull();

        List<UsuarioData> usuariosBd = usuarioService.findAll();
        assertThat(usuariosBd).isNotNull();
        assertThat(usuariosBd.get(0).getNombre()).isEqualTo("Usuario Test");
        assertThat((usuariosBd).size()>0).isTrue();
    }

    @Test
    public void borradoLogicoUsuarioTest() {
        UsuarioData usuario = crearUsuario();
        Long idComercio = usuarioService.findComercio(usuario.getId());
        assertThat(usuario.getId()).isNotNull();
        //Test para comprobar el borrado
        usuarioService.borradoUsuarioLogico(usuario.getId(), false);
        UsuarioData usuarioBd = usuarioService.findById(usuario.getId());
        assertThat(usuarioBd).isNotNull();
        assertThat(usuarioBd.getActivo()).isFalse();

    }

    @Test
    public void listarUsuariosDadoIdComercioTest() {
        UsuarioData usuario = crearUsuario();
        Long idComercio = usuarioService.findComercio(usuario.getId());
        assertThat(usuario.getId()).isNotNull();
        //Test para listar usuario por comercio
        List<UsuarioData> usuariosComercio = usuarioService.findAllByIdComercio(idComercio);
        assertThat((usuariosComercio).size()>1).isTrue();
    }

    @Test
    public void cambiarEstadoComercioModificaEstadoUsuariosTest() {
        UsuarioData usuario = crearUsuario();
        Long idComercio = usuarioService.findComercio(usuario.getId());
        ComercioData comercio = comercioService.recuperarComercio(idComercio);

        assertThat(comercio.getActivo()).isTrue();
        assertThat(usuario.getActivo()).isTrue();

        comercioService.modificarEstadoComercio(comercio.getId(), false);
        comercio = comercioService.recuperarComercio(comercio.getId());
        usuario = usuarioService.findById(usuario.getId());

        assertThat(comercio.getActivo()).isFalse();
        assertThat(usuario.getActivo()).isFalse();
    }


}
