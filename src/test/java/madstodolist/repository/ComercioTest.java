// test/repository/ComercioTest.java

package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.TipoUsuario;
import madstodolist.model.Usuario;
import madstodolist.model.Pais;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

        // Crear el comercio y guardar antes de asociar
        Comercio comercio = new Comercio(cif);
        comercio.setPais_id(pais); // Asocia el país al comercio
        comercioRepository.save(comercio); // Guardar primero el comercio

        TipoUsuario tipoUsuario = new TipoUsuario("default-type");
        tipoUsuarioRepository.save(tipoUsuario);

        // Crear y asociar el usuario al comercio
        Usuario usuario = new Usuario("default@gmail.com");
        usuario.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario("default2@gmail.com");
        usuario2.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario2.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario("default3@gmail.com");
        usuario3.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario3.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario3);

        // Agregar los usuarios al comercio y actualizar
        comercio.getUsuarios().add(usuario);
        comercio.getUsuarios().add(usuario2);
        comercio.getUsuarios().add(usuario3);

        comercioRepository.save(comercio); // Actualizar el comercio con el usuario asociado

        return comercio;
    }


    //
    // Tests modelo Comercio en memoria, sin la conexión con la BD
    //

    @Test
    public void crearComercio() {
        // GIVEN
        Comercio comercio = new Comercio("CIF123456");

        // THEN
        assertThat(comercio.getNombre()).isEqualTo("default-name");
        assertThat(comercio.getCif()).isEqualTo("CIF123456");
        assertThat(comercio.getPais()).isEqualTo("default-country");
        assertThat(comercio.getProvincia()).isEqualTo("default-province");
        assertThat(comercio.getDireccion()).isEqualTo("default-address");
        assertThat(comercio.getIban()).isEqualTo("default-iban");
        assertThat(comercio.getApiKey()).isEqualTo("default-apiKey");
        assertThat(comercio.getUrl_back()).isEqualTo("default-url_back");
    }

    @Test
    public void comprobarIgualdadComerciosSinId() {
        // GIVEN
        Comercio comercio1 = new Comercio("CIF123456");
        Comercio comercio2 = new Comercio("CIF123456");
        Comercio comercio3 = new Comercio("CIF654321");

        // THEN
        assertThat(comercio1).isEqualTo(comercio2);
        assertThat(comercio1).isNotEqualTo(comercio3);
    }

    @Test
    public void comprobarIgualdadComerciosConId() {
        // GIVEN
        Comercio comercio1 = new Comercio("CIF123456");
        Comercio comercio2 = new Comercio("CIF654321");
        Comercio comercio3 = new Comercio("CIF123433");

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
    public void crearYBuscarComercioBaseDatos() {
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
        assertThat(comercioRecuperado.getUsuarios()).hasSize(3);
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

        usuarioDB = usuarioRepository.findById(usuarioDB.getId()).orElse(null);

        // THEN
        assertThat(usuarioDB.getNombre()).isEqualTo("Usuario Uno Modificado");
    }

    @Test
    @Transactional
    public void salvarComercioEnBaseDatosConPaisNoBDLanzaExcepcion() {
        // GIVEN

        Comercio comercio = new Comercio("CIF123456");
        Pais pais = new Pais("default-country");

        // WHEN // THEN

        Assertions.assertThrows(Exception.class, () -> {
            comercioRepository.save(comercio);
        });
    }
}
