// test/repository/IncidenciaTest.java

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
public class IncidenciaTest {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    //
    // Tests modelo Incidencia en memoria, sin la conexión con la BD
    //

    @Test
    public void crearIncidencia() {
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
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo("Incidencia 1");
        incidencia.setDescripcion("Descripción de la incidencia 1");
        incidencia.setValoracion(5);
        incidencia.setRazon_valoracion("Muy bien");

        // WHEN
        incidencia.setUsuario_comercio(usuario);

        // THEN
        assertThat(incidencia.getFecha()).isNotNull();
        assertThat(incidencia.getTitulo()).isEqualTo("Incidencia 1");
        assertThat(incidencia.getDescripcion()).isEqualTo("Descripción de la incidencia 1");
        assertThat(incidencia.getValoracion()).isEqualTo(5);
        assertThat(incidencia.getRazon_valoracion()).isEqualTo("Muy bien");
        assertThat(incidencia.getUsuario_comercio()).isEqualTo(usuario);
    }

    @Test
    public void comprobarIgualdadIncidenciasSinId() {
        // GIVEN
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        Incidencia incidencia1 = new Incidencia();
        incidencia1.setFecha(new Date());
        incidencia1.setTitulo("Incidencia 1");
        incidencia1.setDescripcion("Descripción 1");
        incidencia1.setValoracion(5);
        incidencia1.setRazon_valoracion("Muy bien");
        incidencia1.setUsuario_comercio(usuario);

        Incidencia incidencia2 = new Incidencia();
        incidencia2.setFecha(new Date());
        incidencia2.setTitulo("Incidencia 1");
        incidencia2.setDescripcion("Descripción 1");
        incidencia2.setValoracion(5);
        incidencia2.setRazon_valoracion("Muy bien");
        incidencia2.setUsuario_comercio(usuario);

        Incidencia incidencia3 = new Incidencia();
        incidencia3.setFecha(new Date());
        incidencia3.setTitulo("Incidencia 2");
        incidencia3.setDescripcion("Descripción 2");
        incidencia3.setValoracion(3);
        incidencia3.setRazon_valoracion("Regular");
        incidencia3.setUsuario_comercio(usuario);

        // THEN
        // Como equals solo compara por ID, y ambos IDs son null, son diferentes
        assertThat(incidencia1).isNotEqualTo(incidencia2);
        assertThat(incidencia1).isNotEqualTo(incidencia3);
    }

    @Test
    public void comprobarIgualdadIncidenciasConId() {
        // GIVEN
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
        Incidencia incidencia1 = new Incidencia();
        incidencia1.setFecha(new Date());
        incidencia1.setTitulo("Incidencia 1");
        incidencia1.setDescripcion("Descripción 1");
        incidencia1.setValoracion(5);
        incidencia1.setRazon_valoracion("Muy bien");
        incidencia1.setUsuario_comercio(usuario);
        incidencia1.setId(1L);

        Incidencia incidencia2 = new Incidencia();
        incidencia2.setFecha(new Date());
        incidencia2.setTitulo("Incidencia 2");
        incidencia2.setDescripcion("Descripción 2");
        incidencia2.setValoracion(3);
        incidencia2.setRazon_valoracion("Regular");
        incidencia2.setUsuario_comercio(usuario);
        incidencia2.setId(2L);

        Incidencia incidencia3 = new Incidencia();
        incidencia3.setFecha(new Date());
        incidencia3.setTitulo("Incidencia 3");
        incidencia3.setDescripcion("Descripción 3");
        incidencia3.setValoracion(4);
        incidencia3.setRazon_valoracion("Bueno");
        incidencia3.setUsuario_comercio(usuario);
        incidencia3.setId(1L);

        // THEN
        assertThat(incidencia1).isEqualTo(incidencia3);
        assertThat(incidencia1).isNotEqualTo(incidencia2);
    }

    //
    // Tests IncidenciaRepository.
    //

    @Test
    @Transactional
    public void crearIncidenciaBaseDatos() {
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
        incidencia.setDescripcion("Descripción de la incidencia 1");
        incidencia.setValoracion(5);
        incidencia.setRazon_valoracion("Muy bien");
        incidencia.setUsuario_comercio(usuario);

        // WHEN
        incidenciaRepository.save(incidencia);

        // THEN
        assertThat(incidencia.getId()).isNotNull();

        Incidencia incidenciaBD = incidenciaRepository.findById(incidencia.getId()).orElse(null);
        assertThat(incidenciaBD.getTitulo()).isEqualTo("Incidencia 1");
        assertThat(incidenciaBD.getDescripcion()).isEqualTo("Descripción de la incidencia 1");
        assertThat(incidenciaBD.getValoracion()).isEqualTo(5);
        assertThat(incidenciaBD.getRazon_valoracion()).isEqualTo("Muy bien");
        assertThat(incidenciaBD.getUsuario_comercio()).isEqualTo(usuario);
    }

    @Test
    @Transactional
    public void buscarIncidenciaEnBaseDatos() {
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
        incidencia.setDescripcion("Descripción de la incidencia 1");
        incidencia.setValoracion(5);
        incidencia.setRazon_valoracion("Muy bien");
        incidencia.setUsuario_comercio(usuario);
        incidenciaRepository.save(incidencia);
        Long incidenciaId = incidencia.getId();

        // WHEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidenciaId).orElse(null);

        // THEN
        assertThat(incidenciaBD).isNotNull();
        assertThat(incidenciaBD.getId()).isEqualTo(incidenciaId);
        assertThat(incidenciaBD.getTitulo()).isEqualTo("Incidencia 1");
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
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1");
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
