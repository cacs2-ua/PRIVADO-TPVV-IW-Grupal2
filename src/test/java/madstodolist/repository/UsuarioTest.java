// test/repository/UsuarioTest.java

package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.Incidencia;
import madstodolist.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class UsuarioTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    // Métodos auxiliares para reducir duplicación
    private Comercio crearYGuardarComercio() {
        Comercio comercio = new Comercio(
                "Comercio Ejemplo",
                "B12345678",
                "España",
                "Madrid",
                "Calle Ejemplo 123",
                "ES9121000418450200051332",
                "clave-api-ejemplo",
                "https://mi-comercio.com/backend"
        );
        comercioRepository.save(comercio);
        return comercio;
    }

    private Usuario crearYGuardarUsuario(String email, String nombre, String password, Comercio comercio) {
        Usuario usuario = new Usuario(email, nombre, password, comercio);
        usuarioRepository.save(usuario);
        return usuario;
    }

    private Incidencia crearYGuardarIncidencia(String titulo, String descripcion, int valoracion, Usuario usuario) {
        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo(titulo);
        incidencia.setDescripcion(descripcion);
        incidencia.setValoracion(valoracion);
        incidencia.setRazon_valoracion("Muy bien");
        incidencia.setUsuario_comercio(usuario);
        incidenciaRepository.save(incidencia);
        return incidencia;
    }

    //
    // Tests modelo Usuario en memoria, sin la conexión con la BD
    //

    @Test
    public void crearUsuario() {
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
    public void crearUsuarioBaseDatos() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        // THEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getEmail()).isEqualTo("user@comercio.com");
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Uno");
        assertThat(usuarioBD.getComercio().getNombre()).isEqualTo("Comercio Ejemplo");
    }

    @Test
    @Transactional
    public void buscarUsuarioEnBaseDatos() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        // WHEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);

        // THEN
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Uno");
        assertThat(usuarioBD.getComercio()).isEqualTo(comercio);
    }

    @Test
    @Transactional
    public void buscarUsuarioPorEmail() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        // WHEN
        Usuario usuarioBD = usuarioRepository.findByEmail("user@comercio.com").orElse(null);

        // THEN
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Uno");
    }

    @Test
    @Transactional
    public void unUsuarioTieneUnaListaDeIncidencias() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia1 = crearYGuardarIncidencia("Incidencia 1", "Descripción 1", 5, usuario);
        Incidencia incidencia2 = crearYGuardarIncidencia("Incidencia 2", "Descripción 2", 3, usuario);

        // WHEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);

        // THEN
        assertThat(usuarioBD.getIncidencias_comercio()).containsExactlyInAnyOrder(incidencia1, incidencia2);
    }

    @Test
    @Transactional
    public void añadirUnaIncidenciaAUnUsuarioEnBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        // WHEN
        Incidencia incidencia = crearYGuardarIncidencia("Incidencia 3", "Descripción 3", 4, usuario);

        // THEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(usuarioBD.getIncidencias_comercio()).contains(incidencia);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia = crearYGuardarIncidencia("Incidencia 1", "Descripción 1", 5, usuario);

        // WHEN
        incidencia.setTitulo("Incidencia Modificada");

        // THEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidencia.getId()).orElse(null);
        assertThat(incidenciaBD.getTitulo()).isEqualTo("Incidencia Modificada");
    }

    @Test
    @Transactional
    public void salvarIncidenciaEnBaseDatosConUsuarioNoBDLanzaExcepcion() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio();
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo("Incidencia 1");
        incidencia.setDescripcion("Descripción 1");
        incidencia.setValoracion(5);
        incidencia.setRazon_valoracion("Muy bien");
        incidencia.setUsuario_comercio(usuario);

        // WHEN // THEN
        assertThrows(Exception.class, () -> incidenciaRepository.save(incidencia));
    }
}
