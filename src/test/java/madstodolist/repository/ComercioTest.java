package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.Pais;
import madstodolist.model.TipoUsuario;
import madstodolist.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class ComercioTest {

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository; // Asegúrate de tener este repositorio creado

    private Pais paisEspaña;
    private TipoUsuario tipoDefault;

    @BeforeEach
    public void setUp() {
        // Crear y guardar un Pais único para todos los tests
        paisEspaña = new Pais("España");
        paisEspaña = paisRepository.save(paisEspaña);

        // Crear y guardar un TipoUsuario único para todos los tests
        tipoDefault = new TipoUsuario("default");
        tipoDefault = tipoUsuarioRepository.save(tipoDefault);
    }

    //
    // Tests modelo Comercio en memoria, sin la conexión con la BD
    //

    @Test
    public void crearComercio() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");

        // THEN
        assertThat(comercio.getCif()).isEqualTo("12345678A");
        assertThat(comercio.getNombre()).isEqualTo("default-name");
    }

    @Test
    public void comprobarIgualdadComerciosSinId() {
        // GIVEN
        Comercio comercio1 = new Comercio("12345678A");
        Comercio comercio2 = new Comercio("12345678A");
        Comercio comercio3 = new Comercio("87654321B");

        // THEN
        assertThat(comercio1).isEqualTo(comercio2);
        assertThat(comercio1).isNotEqualTo(comercio3);
    }

    @Test
    public void comprobarIgualdadComerciosConId() {
        // GIVEN
        Comercio comercio1 = new Comercio("12345678A");
        Comercio comercio2 = new Comercio("87654321B");
        Comercio comercio3 = new Comercio("12345678A");

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
    public void guardarComercioEnBaseDatos() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");
        comercio.setNombre("Comercio Ejemplo");
        comercio.setPais("España");
        comercio.setProvincia("Madrid");
        comercio.setDireccion("Calle Falsa 123");
        comercio.setIban("ES9121000418450200051332");
        comercio.setApiKey("apikey123");
        comercio.setUrl_back("https://example.com/back");

        // Asignar el Pais previamente guardado
        comercio.setPais_id(paisEspaña);

        // WHEN
        comercioRepository.save(comercio);

        // THEN
        assertThat(comercio.getId()).isNotNull();

        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        assertThat(comercioBD).isNotNull();
        assertThat(comercioBD.getNombre()).isEqualTo("Comercio Ejemplo");
        assertThat(comercioBD.getCif()).isEqualTo("12345678A");
        assertThat(comercioBD.getPais()).isEqualTo("España");
        assertThat(comercioBD.getProvincia()).isEqualTo("Madrid");
        assertThat(comercioBD.getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(comercioBD.getIban()).isEqualTo("ES9121000418450200051332");
        assertThat(comercioBD.getApiKey()).isEqualTo("apikey123");
        assertThat(comercioBD.getUrl_back()).isEqualTo("https://example.com/back");
        assertThat(comercioBD.getPais_id()).isEqualTo(paisEspaña);
    }

    @Test
    @Transactional
    public void salvarComercioConCifDuplicadoLanzaExcepcion() {
        // GIVEN
        Comercio comercio1 = new Comercio("12345678A");
        comercio1.setPais_id(paisEspaña);
        comercioRepository.save(comercio1);

        Comercio comercio2 = new Comercio("12345678A"); // Mismo CIF
        comercio2.setPais_id(paisEspaña); // Asignar el mismo Pais existente

        // WHEN // THEN
        Assertions.assertThrows(Exception.class, () -> {
            comercioRepository.save(comercio2);
            comercioRepository.flush(); // Forzar la ejecución de la operación
        });
    }

    @Test
    @Transactional
    public void unComercioTieneUnaListaDeUsuarios() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");
        comercio.setPais_id(paisEspaña);
        comercioRepository.save(comercio);
        Long comercioId = comercio.getId();

        // Crear usuarios con TipoUsuario previamente guardado
        Usuario usuario1 = new Usuario("user1@ua");
        usuario1.setTipo(tipoDefault);
        usuario1.setComercio(comercio);

        Usuario usuario2 = new Usuario("user2@ua");
        usuario2.setTipo(tipoDefault);
        usuario2.setComercio(comercio);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        // WHEN
        Comercio comercioRecuperado = comercioRepository.findById(comercioId).orElse(null);

        // THEN
        assertThat(comercioRecuperado).isNotNull();
        assertThat(comercioRecuperado.getUsuarios()).hasSize(2).containsExactlyInAnyOrder(usuario1, usuario2);
    }

    @Test
    @Transactional
    public void añadirUnUsuarioAUnComercioEnBD() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");
        comercio.setPais_id(paisEspaña);
        comercioRepository.save(comercio);
        Long comercioId = comercio.getId();

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercioId).orElse(null);

        // Crear un nuevo usuario con TipoUsuario previamente guardado
        Usuario usuario = new Usuario("newuser@ua");
        usuario.setTipo(tipoDefault);
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
        Comercio comercio = new Comercio("12345678A");
        comercio.setNombre("Comercio Original");
        comercio.setPais_id(paisEspaña);
        comercioRepository.save(comercio);

        // Recuperar el comercio
        Long comercioId = comercio.getId();
        comercio = comercioRepository.findById(comercioId).orElse(null);

        // WHEN
        comercio.setNombre("Comercio Actualizado");

        // THEN
        Comercio comercioBD = comercioRepository.findById(comercioId).orElse(null);
        assertThat(comercioBD.getNombre()).isEqualTo("Comercio Actualizado");
    }

    @Test
    @Transactional
    public void eliminarComercioEnBD() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");
        comercio.setPais_id(paisEspaña);
        comercioRepository.save(comercio);
        Long comercioId = comercio.getId();

        // WHEN
        comercioRepository.delete(comercio);

        // THEN
        Comercio comercioBD = comercioRepository.findById(comercioId).orElse(null);
        assertThat(comercioBD).isNull();
    }

    @Test
    @Transactional
    public void buscarComercioPorCif() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");
        comercio.setNombre("Comercio Ejemplo");
        comercio.setPais_id(paisEspaña);
        comercioRepository.save(comercio);

        // WHEN
        Comercio comercioBD = comercioRepository.findByCif("12345678A").orElse(null);

        // THEN
        assertThat(comercioBD).isNotNull();
        assertThat(comercioBD.getNombre()).isEqualTo("Comercio Ejemplo");
    }

    @Test
    @Transactional
    public void existsByCifTrue_ShouldReturnTrueWhenCifExists() {
        // GIVEN
        Comercio comercio = new Comercio("12345678A");
        comercio.setPais_id(paisEspaña);
        comercioRepository.save(comercio);

        // WHEN
        boolean exists = comercioRepository.existsByCif("12345678A");

        // THEN
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    public void existsByCifTrue_ShouldReturnFalseWhenCifDoesNotExist() {
        // GIVEN
        // No comercios en la base de datos

        // WHEN
        boolean exists = comercioRepository.existsByCif("99999999Z");

        // THEN
        assertThat(exists).isFalse();
    }

    @Test
    @Transactional
    public void findAllComercios_ShouldReturnAllComercios() {
        // GIVEN
        Comercio comercio1 = new Comercio("12345678A");
        Comercio comercio2 = new Comercio("87654321B");

        Pais paisFrancia = new Pais("Francia");
        paisFrancia = paisRepository.save(paisFrancia);

        comercio1.setPais_id(paisEspaña);
        comercio2.setPais_id(paisFrancia);

        comercioRepository.save(comercio1);
        comercioRepository.save(comercio2);

        // WHEN
        Iterable<Comercio> comercios = comercioRepository.findAll();

        // THEN
        assertThat(comercios).containsExactlyInAnyOrder(comercio1, comercio2);
    }
}
