// test/repository/ComercioTest.java

package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class ComercioTest {

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //
    // Tests modelo Comercio en memoria, sin la conexión con la BD
    //

    @Test
    public void crearComercio() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");

        // THEN
        assertThat(comercio.getNombre()).isEqualTo("Comercio A");
        assertThat(comercio.getCif()).isEqualTo("CIF123456");
        assertThat(comercio.getPais()).isEqualTo("España");
        assertThat(comercio.getProvincia()).isEqualTo("Madrid");
        assertThat(comercio.getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(comercio.getIban()).isEqualTo("ES9121000418450200051332");
        assertThat(comercio.getApi_key()).isEqualTo("API_KEY_123");
        assertThat(comercio.getUrl_back()).isEqualTo("http://url-back.com");
    }

    @Test
    public void comprobarIgualdadComerciosSinId() {
        // GIVEN
        Comercio comercio1 = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        Comercio comercio2 = new Comercio("Comercio B", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        Comercio comercio3 = new Comercio("Comercio C", "CIF654321", "España", "Madrid", "Calle Verdadera 456", "ES9121000418450200051333", "API_KEY_456", "http://url-back2.com");

        // THEN
        assertThat(comercio1).isEqualTo(comercio2);
        assertThat(comercio1).isNotEqualTo(comercio3);
    }

    @Test
    public void comprobarIgualdadComerciosConId() {
        // GIVEN
        Comercio comercio1 = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        Comercio comercio2 = new Comercio("Comercio B", "CIF654321", "España", "Madrid", "Calle Verdadera 456", "ES9121000418450200051333", "API_KEY_456", "http://url-back2.com");
        Comercio comercio3 = new Comercio("Comercio C", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");

        comercio1.setId(1L);
        comercio2.setId(2L);
        comercio3.setId(1L);

        // THEN
        assertThat(comercio1).isEqualTo(comercio3);
        assertThat(comercio1).isNotEqualTo(comercio2);
    }

    //
    // Tests ComercioRepository.
    //

    @Test
    @Transactional
    public void crearComercioBaseDatos() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");

        // WHEN
        comercioRepository.save(comercio);

        // THEN
        assertThat(comercio.getId()).isNotNull();

        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        assertThat(comercioBD.getNombre()).isEqualTo("Comercio A");
        assertThat(comercioBD.getCif()).isEqualTo("CIF123456");
        assertThat(comercioBD.getPais()).isEqualTo("España");
        assertThat(comercioBD.getProvincia()).isEqualTo("Madrid");
        assertThat(comercioBD.getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(comercioBD.getIban()).isEqualTo("ES9121000418450200051332");
        assertThat(comercioBD.getApi_key()).isEqualTo("API_KEY_123");
        assertThat(comercioBD.getUrl_back()).isEqualTo("http://url-back.com");
    }

    @Test
    @Transactional
    public void buscarComercioEnBaseDatos() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);
        Long comercioId = comercio.getId();

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercioId).orElse(null);

        // THEN
        assertThat(comercioBD).isNotNull();
        assertThat(comercioBD.getId()).isEqualTo(comercioId);
        assertThat(comercioBD.getNombre()).isEqualTo("Comercio A");
    }

    @Test
    @Transactional
    public void buscarComercioPorCif() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);

        // WHEN
        Comercio comercioBD = comercioRepository.findByCif("CIF123456").orElse(null);

        // THEN
        assertThat(comercioBD.getNombre()).isEqualTo("Comercio A");
    }

    @Test
    @Transactional
    public void unComercioTieneUnaListaDeUsuarios() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);
        Long comercioId = comercio.getId();

        Usuario usuario1 = new Usuario("user1@comercio.com", "Usuario Uno", "password1");
        usuario1.setComercio(comercio);
        usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario("user2@comercio.com", "Usuario Dos", "password2");
        usuario2.setComercio(comercio);
        usuarioRepository.save(usuario2);

        // WHEN
        Comercio comercioRecuperado = comercioRepository.findById(comercioId).orElse(null);

        // THEN
        assertThat(comercioRecuperado.getUsuarios()).hasSize(2);
    }

    @Test
    @Transactional
    public void añadirUnUsuarioAUnComercioEnBD() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);
        Long comercioId = comercio.getId();

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercioId).orElse(null);
        Usuario usuario = new Usuario("user3@comercio.com", "Usuario Tres", "password3");
        usuario.setComercio(comercioBD);
        usuarioRepository.save(usuario);
        Long usuarioId = usuario.getId();

        // THEN
        Usuario usuarioBD = usuarioRepository.findById(usuarioId).orElse(null);
        assertThat(usuarioBD).isEqualTo(usuario);
        assertThat(usuarioBD.getComercio()).isEqualTo(comercioBD);

        comercioBD = comercioRepository.findById(comercioId).orElse(null);
        assertThat(comercioBD.getUsuarios()).contains(usuarioBD);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        usuario.setComercio(comercio);
        usuarioRepository.save(usuario);

        // Recuperamos el usuario
        Long usuarioId = usuario.getId();
        usuario = usuarioRepository.findById(usuarioId).orElse(null);

        // WHEN
        usuario.setNombre("Usuario Uno Modificado");

        // THEN
        Usuario usuarioBD = usuarioRepository.findById(usuarioId).orElse(null);
        assertThat(usuarioBD.getNombre()).isEqualTo(usuario.getNombre());
    }
}
