// test/repository/UsuarioTest.java

package madstodolist.repository;

import madstodolist.model.*;
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
    private ComercioRepository comercioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaisRepository paisRepository;

    private Usuario crearYGuardarUsuario(String email1, String email2, String email3) {
        // Crear y guardar entidades dependientes
        Pais pais = new Pais("default-country");
        paisRepository.save(pais);

        // Crear el comercio y guardar antes de asociar
        Comercio comercio = new Comercio("default-cif");
        comercio.setPais_id(pais); // Asocia el país al comercio
        comercioRepository.save(comercio); // Guardar primero el comercio

        TipoUsuario tipoUsuario = new TipoUsuario("default-type");
        tipoUsuarioRepository.save(tipoUsuario);

        // Crear y asociar el usuario al comercio
        Usuario usuario = new Usuario(email1);
        usuario.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario(email2);
        usuario2.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario2.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario(email3);
        usuario3.setTipo(tipoUsuario); // Asocia el tipo al usuario
        usuario3.setComercio(comercio); // Asociar comercio al usuario
        usuarioRepository.save(usuario3);

        // Agregar los usuarios al comercio y actualizar
        comercio.getUsuarios().add(usuario);
        comercio.getUsuarios().add(usuario2);
        comercio.getUsuarios().add(usuario3);

        comercioRepository.save(comercio); // Actualizar el comercio con el usuario asociado

        return usuario;
    }

    //
    // Tests modelo Usuario en memoria, sin la conexión con la BD
    //

    @Test
    public void crearUsuario() {
        // GIVEN
        Usuario usuario = new Usuario("user@comercio.com");

        // THEN
        assertThat(usuario.getEmail()).isEqualTo("user@comercio.com");
        assertThat(usuario.getNombre()).isEqualTo("default");
        assertThat(usuario.getContrasenya()).isEqualTo("default");
    }

    @Test
    public void comprobarIgualdadUsuariosSinId() {
        // GIVEN
        Usuario usuario1 = new Usuario("user@comercio.com");
        Usuario usuario2 = new Usuario("user@comercio.com");
        Usuario usuario3 = new Usuario("user2@comercio.com");

        // THEN
        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1).isNotEqualTo(usuario3);
    }

    @Test
    public void comprobarIgualdadUsuariosConId() {
        // GIVEN
        Usuario usuario1 = new Usuario("user1@comercio.com");
        Usuario usuario2 = new Usuario("user2@comercio.com");
        Usuario usuario3 = new Usuario("user3@comercio.com");

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
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "user2@comercio.com", "user3@comercio.com");

        // THEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getEmail()).isEqualTo("user@comercio.com");
        assertThat(usuarioBD.getNombre()).isEqualTo("default");
        assertThat(usuarioBD.getComercio().getNombre()).isEqualTo("default-name");
    }

    @Test
    @Transactional
    public void buscarUsuarioEnBaseDatos() {
        // GIVEN
        Usuario usuario = crearYGuardarUsuario("user@comercio.com", "user2@comercio.com", "user3@comercio.com");

        // WHEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);

        // THEN
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getNombre()).isEqualTo("default");
        //assertThat(usuarioBD.getComercio()).isEqualTo(comercio);
    }

    @Test
    @Transactional
    public void buscarUsuarioPorEmail() {
        // GIVEN
        crearYGuardarUsuario("user@comercio.com", "user2@comercio.com", "user3@comercio.com");

        // WHEN
        Usuario usuarioBD = usuarioRepository.findByEmail("user@comercio.com").orElse(null);

        // THEN
        assertThat(usuarioBD.getNombre()).isEqualTo("default");
    }

    /*
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
    */
}
