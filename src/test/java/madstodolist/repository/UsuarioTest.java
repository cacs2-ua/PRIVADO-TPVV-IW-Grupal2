// test/repository/UsuarioTest.java

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
public class UsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    //
    // Tests modelo Usuario en memoria, sin la conexión con la BD
    //

    @Test
    public void crearUsuario() throws Exception {
        // GIVEN
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");

        // THEN
        assertThat(usuario.getEmail()).isEqualTo("user@comercio.com");
        assertThat(usuario.getNombre()).isEqualTo("Usuario Uno");
        assertThat(usuario.getContrasenya()).isEqualTo("password1");
    }

    @Test
    public void comprobarIgualdadUsuariosSinId() {
        // GIVEN
        Usuario usuario1 = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        Usuario usuario2 = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        Usuario usuario3 = new Usuario("another@comercio.com", "Usuario Dos", "password2");

        // THEN
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1).isNotEqualTo(usuario3);
    }

    @Test
    public void comprobarIgualdadUsuariosConId() {
        // GIVEN
        Usuario usuario1 = new Usuario("user1@comercio.com", "Usuario Uno", "password1");
        Usuario usuario2 = new Usuario("user2@comercio.com", "Usuario Dos", "password2");
        Usuario usuario3 = new Usuario("user3@comercio.com", "Usuario Tres", "password3");

        usuario1.setId(1L);
        usuario2.setId(2L);
        usuario3.setId(1L);

        // THEN
        assertThat(usuario1).isEqualTo(usuario3);
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    //
    // Tests UsuarioRepository.
    //

    @Test
    @Transactional
    public void crearUsuarioBaseDatos() throws Exception {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);

        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        usuario.setComercio(comercio);

        // WHEN
        usuarioRepository.save(usuario);

        // THEN
        assertThat(usuario.getId()).isNotNull();

        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(usuarioBD.getEmail()).isEqualTo("user@comercio.com");
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Uno");
        assertThat(usuarioBD.getContrasenya()).isEqualTo("password1");
        assertThat(usuarioBD.getComercio().getNombre()).isEqualTo("Comercio A");
    }

    @Test
    @Transactional
    public void buscarUsuarioEnBaseDatos() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        usuario.setComercio(comercio);
        usuarioRepository.save(usuario);
        Long usuarioId = usuario.getId();

        // WHEN
        Usuario usuarioBD = usuarioRepository.findById(usuarioId).orElse(null);

        // THEN
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getId()).isEqualTo(usuarioId);
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Uno");
        assertThat(usuarioBD.getComercio()).isEqualTo(comercio);
    }

    @Test
    @Transactional
    public void buscarUsuarioPorEmail() {
        // GIVEN
        Comercio comercio = new Comercio("Comercio A", "CIF123456", "España", "Madrid", "Calle Falsa 123", "ES9121000418450200051332", "API_KEY_123", "http://url-back.com");
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        usuario.setComercio(comercio);
        usuarioRepository.save(usuario);

        // WHEN
        Usuario usuarioBD = usuarioRepository.findByEmail("user@comercio.com").orElse(null);

        // THEN
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Uno");
    }
}
