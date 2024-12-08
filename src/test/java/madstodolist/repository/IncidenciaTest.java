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

    // Métodos auxiliares para reducir duplicación

    private Comercio crearYGuardarComercio(String nif) {
        Comercio comercio = new Comercio(nif);
        comercioRepository.save(comercio);
        return comercio;
    }

    private Usuario crearYGuardarUsuario(String email, String nombre, String password, Comercio comercio) {
        Usuario usuario = new Usuario(email, nombre, password, comercio);
        usuarioRepository.save(usuario);
        return usuario;
    }

    private Incidencia crearIncidencia(Usuario usuario, String titulo, String descripcion, int valoracion, String razonValoracion) {
        Incidencia incidencia = new Incidencia();
        incidencia.setFecha(new Date());
        incidencia.setTitulo(titulo);
        incidencia.setDescripcion(descripcion);
        incidencia.setValoracion(valoracion);
        incidencia.setRazon_valoracion(razonValoracion);
        incidencia.setUsuario_comercio(usuario);
        return incidencia;
    }

    //
    // Tests modelo Incidencia en memoria, sin la conexión con la BD
    //

    @Test
    public void crearIncidencia() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        Incidencia incidencia = crearIncidencia(usuario, "Incidencia 1", "Descripción de la incidencia 1", 5, "Muy bien");

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
    @Transactional
    public void laListaDeIncidenciasDeUnUsuarioSeActualizaEnMemoriaConUnaNuevaIncidencia() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia1 = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");
        incidenciaRepository.save(incidencia1);

        // WHEN
        Incidencia nuevaIncidencia = crearIncidencia(usuario, "Incidencia 2", "Descripción 2", 4, "Buena");
        usuario.addIncidencia_comercio(nuevaIncidencia); // Actualizamos en memoria

        // THEN
        assertThat(usuario.getIncidencias_comercio()).hasSize(2);
        assertThat(usuario.getIncidencias_comercio()).contains(incidencia1, nuevaIncidencia);
        assertThat(nuevaIncidencia.getUsuario_comercio()).isEqualTo(usuario);
    }


    @Test
    public void comprobarIgualdadIncidenciasSinId() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia1 = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");
        Incidencia incidencia2 = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");
        Incidencia incidencia3 = crearIncidencia(usuario, "Incidencia 2", "Descripción 2", 3, "Regular");

        // THEN
        // Como equals solo compara por ID, y ambos IDs son null, son diferentes
        assertThat(incidencia1).isNotEqualTo(incidencia2);
        assertThat(incidencia1).isNotEqualTo(incidencia3);
    }

    @Test
    public void comprobarIgualdadIncidenciasConId() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia1 = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");
        incidencia1.setId(1L);

        Incidencia incidencia2 = crearIncidencia(usuario, "Incidencia 2", "Descripción 2", 3, "Regular");
        incidencia2.setId(2L);

        Incidencia incidencia3 = crearIncidencia(usuario, "Incidencia 3", "Descripción 3", 4, "Bueno");
        incidencia3.setId(1L);

        // THEN
        assertThat(incidencia1).isEqualTo(incidencia3);
        assertThat(incidencia1).isNotEqualTo(incidencia2);
    }

    //
    // Tests IncidenciaRepository
    //

    @Test
    @Transactional
    public void crearIncidenciaBaseDatos() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        Incidencia incidencia = crearIncidencia(usuario, "Incidencia 1", "Descripción de la incidencia 1", 5, "Muy bien");

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
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        Incidencia incidencia = crearIncidencia(usuario, "Incidencia 1", "Descripción de la incidencia 1", 5, "Muy bien");
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
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia1 = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");
        incidenciaRepository.save(incidencia1);

        Incidencia incidencia2 = crearIncidencia(usuario, "Incidencia 2", "Descripción 2", 3, "Regular");
        incidenciaRepository.save(incidencia2);

        // WHEN
        Usuario usuarioRecuperado = usuarioRepository.findById(usuario.getId()).orElse(null);

        // THEN
        assertThat(usuarioRecuperado.getIncidencias_comercio()).hasSize(2);
    }

    @Test
    @Transactional
    public void añadirUnaIncidenciaAUnUsuarioEnBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia = crearIncidencia(usuario, "Incidencia 3", "Descripción 3", 4, "Bueno");
        incidenciaRepository.save(incidencia);

        // THEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidencia.getId()).orElse(null);
        assertThat(incidenciaBD).isEqualTo(incidencia);
        assertThat(incidenciaBD.getUsuario_comercio()).isEqualTo(usuario);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "Usuario Uno", "password1", comercio);

        Incidencia incidencia = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");
        incidenciaRepository.save(incidencia);

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
        Comercio comercio = crearYGuardarComercio("default-nif");
        Usuario usuario = new Usuario("user@comercio.com", "Usuario Uno", "password1", comercio);
        Incidencia incidencia = crearIncidencia(usuario, "Incidencia 1", "Descripción 1", 5, "Muy bien");

        // WHEN // THEN
        assertThrows(Exception.class, () -> incidenciaRepository.save(incidencia));
    }


}
