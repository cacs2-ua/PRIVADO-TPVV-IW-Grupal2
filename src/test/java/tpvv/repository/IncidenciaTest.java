
package tpvv.repository;

import tpvv.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class IncidenciaTest {

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private EstadoIncidenciaRepository estadoIncidenciaRepository;

    private Incidencia crearYGuardarIncidencia(String titulo) {
        Pais pais = new Pais("default-country");
        paisRepository.save(pais);


        Comercio comercio = new Comercio("default-cif");
        comercio.setPais_id(pais); // Asocia el país al comercio
        comercioRepository.save(comercio); // Guardar primero el comercio

        TipoUsuario tipoUsuario = new TipoUsuario("default-type");
        tipoUsuarioRepository.save(tipoUsuario);

        Usuario usuario = new Usuario("default-email");
        usuario.setTipo(tipoUsuario);
        usuario.setComercio(comercio);
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario("default-email2");
        usuario2.setTipo(tipoUsuario);
        usuario2.setComercio(comercio);
        usuarioRepository.save(usuario2);

        comercio.getUsuarios().add(usuario);

        comercioRepository.save(comercio);

        EstadoIncidencia estadoIncidencia = new EstadoIncidencia("default-state");
        estadoIncidenciaRepository.save(estadoIncidencia);

        Incidencia incidencia = new Incidencia(titulo);
        incidencia.setUsuario_comercio(usuario);
        incidencia.setUsuario_tecnico(usuario2);
        incidencia.setEstado(estadoIncidencia);

        incidenciaRepository.save(incidencia);

        return incidencia;
    }

    @Test
    public void crearIncidencia() {
        // GIVEN
        Incidencia incidencia = new Incidencia("Incidencia de prueba");

        // THEN
        assertThat(incidencia.getTitulo()).isEqualTo("Incidencia de prueba");
    }

    @Test
    public void comprobarIgualdadIncidenciasConId() {
        // GIVEN
        Incidencia incidencia1 = new Incidencia("Incidencia 1");
        Incidencia incidencia2 = new Incidencia("Incidencia 2");
        Incidencia incidencia3 = new Incidencia("Incidencia 3");

        incidencia1.setId(1L);
        incidencia2.setId(2L);
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
    public void crearYBuscarIncidenciaBaseDatos() {
        // GIVEN
        Incidencia incidencia = crearYGuardarIncidencia("Incidencia de base de datos");

        // THEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidencia.getId()).orElse(null);
        assertThat(incidenciaBD).isNotNull();
        assertThat(incidenciaBD.getTitulo()).isEqualTo("Incidencia de base de datos");
        assertThat(incidenciaBD.getEstado().getNombre()).isEqualTo("default-state");
    }


    @Test
    @Transactional
    public void salvarIncidenciaEnBaseDatosConUsuarioNoBDLanzaExcepcion() {
        // GIVEN
        // Una incidencia nueva que no está en la BD y asociada a Usuarios no persistentes
        Incidencia incidencia = new Incidencia("Nueva incidencia");
        Usuario usuarioComercio = new Usuario("comercio-no-persistente@empresa.com");
        Usuario usuarioTecnico = new Usuario("tecnico-no-persistente@empresa.com");
        // No se guardan los usuarios en la BD

        incidencia.setUsuario_comercio(usuarioComercio);
        incidencia.setUsuario_tecnico(usuarioTecnico);

        EstadoIncidencia estadoIncidencia = new EstadoIncidencia("pendiente");
        estadoIncidenciaRepository.save(estadoIncidencia);
        incidencia.setEstado(estadoIncidencia);

        // WHEN // THEN
        // Se lanza una excepción al intentar salvar la incidencia en la BD debido a Usuarios no persistentes
        Assertions.assertThrows(Exception.class, () -> {
            incidenciaRepository.save(incidencia);
        });
    }

    //
    // Tests adicionales para verificar relaciones
    //

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Incidencia incidencia = crearYGuardarIncidencia("Incidencia para modificar");

        // WHEN
        Incidencia incidenciaBD = incidenciaRepository.findById(incidencia.getId()).orElse(null);
        incidenciaBD.setDescripcion("Descripción Modificada");
        incidenciaRepository.save(incidenciaBD);

        // Recuperar la Incidencia nuevamente para verificar el cambio
        Incidencia incidenciaModificada = incidenciaRepository.findById(incidencia.getId()).orElse(null);

        // THEN
        assertThat(incidenciaModificada).isNotNull();
        assertThat(incidenciaModificada.getDescripcion()).isEqualTo("Descripción Modificada");
    }
    
}
