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

    @Test
    @Transactional
    public void unUsuarioTieneUnaListaDeIncidencias() {
        // GIVEN
        Comercio comercio = new Comercio(
                "Comercio Ejemplo",     // nombre
                "B12345678",            // cif
                "España",               // país
                "Madrid",               // provincia
                "Calle Ejemplo 123",    // dirección
                "ES9121000418450200051332", // IBAN
                "clave-api-ejemplo",    // api_key
                "https://mi-comercio.com/backend" // url_back
        );
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        usuarioRepository.save(usuario);

        Long usuarioId = usuario.getId();

        Incidencia incidencia1 = new Incidencia();
        incidencia1.setFecha(new Date());
        incidencia1.setTitulo("Incidencia 1");
        incidencia1.setDescripcion("Descripción 1");
        incidencia1.setValoracion(5);
        incidencia1.setRazon_valoracion("Muy bien");
        incidencia1.setUsuario_comercio(usuario);
        incidenciaRepository.save(incidencia1);

        Incidencia incidencia2 = new Incidencia();
        incidencia2.setFecha(new Date());
        incidencia2.setTitulo("Incidencia 2");
        incidencia2.setDescripcion("Descripción 2");
        incidencia2.setValoracion(3);
        incidencia2.setRazon_valoracion("Regular");
        incidencia2.setUsuario_comercio(usuario);
        incidenciaRepository.save(incidencia2);

        // WHEN
        Usuario usuarioRecuperado = usuarioRepository.findById(usuarioId).orElse(null);

        // THEN
        assertThat(usuarioRecuperado.getIncidencias_comercio()).hasSize(2);
        assertThat(usuarioRecuperado.getIncidencias_comercio()).contains(incidencia1, incidencia2);
    }

    @Test
    @Transactional
    public void añadirUnaIncidenciaAUnUsuarioEnBD() {
        // GIVEN
        Comercio comercio = new Comercio(
                "Comercio Ejemplo",     // nombre
                "B12345678",            // cif
                "España",               // país
                "Madrid",               // provincia
                "Calle Ejemplo 123",    // dirección
                "ES9121000418450200051332", // IBAN
                "clave-api-ejemplo",    // api_key
                "https://mi-comercio.com/backend" // url_back
        );
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        usuarioRepository.save(usuario);

        Long usuarioId = usuario.getId();

        // WHEN
        Usuario usuarioBD = usuarioRepository.findById(usuarioId).orElse(null);
        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo("Incidencia 3");
        incidencia.setDescripcion("Descripción 3");
        incidencia.setValoracion(4);
        incidencia.setRazon_valoracion("Bueno");
        incidencia.setUsuario_comercio(usuarioBD);
        incidenciaRepository.save(incidencia);
        Long incidenciaId = incidencia.getId();

        // THEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidenciaId).orElse(null);
        assertThat(incidenciaBD).isEqualTo(incidencia);
        assertThat(incidenciaBD.getUsuario_comercio()).isEqualTo(usuarioBD);

        usuarioBD = usuarioRepository.findById(usuarioId).orElse(null);
        assertThat(usuarioBD.getIncidencias_comercio()).contains(incidenciaBD);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Comercio comercio = new Comercio(
                "Comercio Ejemplo",     // nombre
                "B12345678",            // cif
                "España",               // país
                "Madrid",               // provincia
                "Calle Ejemplo 123",    // dirección
                "ES9121000418450200051332", // IBAN
                "clave-api-ejemplo",    // api_key
                "https://mi-comercio.com/backend" // url_back
        );
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        usuarioRepository.save(usuario);



        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo("Incidencia 1");
        incidencia.setDescripcion("Descripción 1");
        incidencia.setValoracion(5);
        incidencia.setRazon_valoracion("Muy bien");
        incidencia.setUsuario_comercio(usuario);
        incidenciaRepository.save(incidencia);

        // Recuperamos la incidencia
        Long incidenciaId = incidencia.getId();
        incidencia = incidenciaRepository.findById(incidenciaId).orElse(null);

        // WHEN
        incidencia.setTitulo("Incidencia Modificada");

        // THEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidenciaId).orElse(null);
        assertThat(incidenciaBD.getTitulo()).isEqualTo("Incidencia Modificada");
    }

    @Test
    @Transactional
    public void salvarIncidenciaEnBaseDatosConUsuarioNoBDLanzaExcepcion() {
        // GIVEN
        Comercio comercio = new Comercio(
                "Comercio Ejemplo",     // nombre
                "B12345678",            // cif
                "España",               // país
                "Madrid",               // provincia
                "Calle Ejemplo 123",    // dirección
                "ES9121000418450200051332", // IBAN
                "clave-api-ejemplo",    // api_key
                "https://mi-comercio.com/backend" // url_back
        );
        comercioRepository.save(comercio);
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo("Incidencia 1");
        incidencia.setDescripcion("Descripción 1");
        incidencia.setValoracion(5);
        incidencia.setRazon_valoracion("Muy bien");
        incidencia.setUsuario_comercio(usuario);

        // WHEN // THEN
        assertThrows(Exception.class, () -> {
            incidenciaRepository.save(incidencia);
        });
    }




}
