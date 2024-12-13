// test/repository/ComercioTest.java

package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.TipoUsuario;
import madstodolist.model.Usuario;
import madstodolist.model.Pais;
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
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaisRepository paisRepository;

    // Métodos auxiliares para reducir duplicación

    private Comercio crearYGuardarComercio(String cif) {
        // Crear y guardar entidades dependientes
        Pais pais = new Pais("default-country");
        paisRepository.save(pais);

        TipoUsuario tipoUsuario = new TipoUsuario("default-type");
        tipoUsuarioRepository.save(tipoUsuario);

        // Crear el comercio y guardar antes de asociar
        Comercio comercio = new Comercio(cif);
        comercio.setPais_id(pais); // Asocia el país al comercio
        comercioRepository.save(comercio); // Guardar primero el comercio

        // Crear y asociar el usuario al comercio
        Usuario usuario = new Usuario("default@gmail.com");
        usuario.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario("default2@gmail.com");
        usuario2.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario2.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario2);

        // Agregar el usuario al comercio y actualizar
        comercio.getUsuarios().add(usuario);
        comercio.getUsuarios().add(usuario2);
        comercioRepository.save(comercio); // Actualizar el comercio con el usuario asociado

        return comercio;
    }


    private Usuario crearYGuardarUsuario(String email) {
        Usuario usuario = new Usuario(email);
        usuarioRepository.save(usuario);
        return usuario;
    }

    private Pais crearYGuardarPais(String nombre) {
        Pais pais = new Pais(nombre);
        paisRepository.save(pais);
        return pais;
    }

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
        assertThat(comercio.getApiKey()).isEqualTo("API_KEY_123");
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
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // THEN
        assertThat(comercio.getId()).isNotNull();

        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        assertThat(comercioBD.getNombre()).isEqualTo("default-name");
        assertThat(comercioBD.getCif()).isEqualTo("CIF123456");
    }

    @Test
    @Transactional
    public void buscarComercioEnBaseDatos() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");
        Long comercioId = comercio.getId();

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercioId).orElse(null);

        // THEN
        assertThat(comercioBD).isNotNull();
        assertThat(comercioBD.getId()).isEqualTo(comercioId);
        assertThat(comercioBD.getNombre()).isEqualTo("default-name");
    }

    @Test
    @Transactional
    public void buscarComercioPorCif() {
        // GIVEN
        crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioBD = comercioRepository.findByCif("CIF123456").orElse(null);

        // THEN
        assertThat(comercioBD.getNombre()).isEqualTo("default-name");
    }

    @Test
    @Transactional
    public void unComercioTieneUnaListaDeUsuarios() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioRecuperado = comercioRepository.findById(comercio.getId()).orElse(null);

        // THEN
        assertThat(comercioRecuperado.getUsuarios()).hasSize(2);
    }

    @Test
    @Transactional
    public void añadirUnUsuarioAUnComercioEnBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        Usuario usuario = comercioBD.getUsuarios().iterator().next();

        // THEN
        assertThat(comercioBD.getUsuarios()).contains(usuario);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        Usuario usuarioDB = comercioBD.getUsuarios().iterator().next();
        usuarioDB.setNombre("Usuario Uno Modificado");

        // THEN
        assertThat(usuarioDB.getNombre()).isEqualTo("Usuario Uno Modificado");
    }
}
