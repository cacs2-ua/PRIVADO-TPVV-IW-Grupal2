package madstodolist.repository;

import madstodolist.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class UsuarioTest {


    @Autowired
    private UsuarioRepository usuarioRepository;

    //
    // Tests modelo Usuario en memoria, sin la conexión con la BD
    //

    @Test
    public void crearUsuario() throws Exception {

        // GIVEN
        // Creado un nuevo usuario,
        Usuario usuario = new Usuario("juan.gutierrez@gmail.com");

        // WHEN
        // actualizamos sus propiedades usando los setters,

        usuario.setNombre("Juan Gutiérrez");
        usuario.setPassword("12345678");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        usuario.setFechaNacimiento(sdf.parse("1997-02-20"));

        // THEN
        // los valores actualizados quedan guardados en el usuario y se
        // pueden recuperar con los getters.

        assertThat(usuario.getEmail()).isEqualTo("juan.gutierrez@gmail.com");
        assertThat(usuario.getNombre()).isEqualTo("Juan Gutiérrez");
        assertThat(usuario.getPassword()).isEqualTo("12345678");
        assertThat(usuario.getFechaNacimiento()).isEqualTo(sdf.parse("1997-02-20"));
    }

    @Test
    public void comprobarIgualdadUsuariosSinId() {
        // GIVEN
        // Creados tres usuarios sin identificador, y dos de ellas con
        // el mismo e-mail

        Usuario usuario1 = new Usuario("juan.gutierrez@gmail.com");
        Usuario usuario2 = new Usuario("juan.gutierrez@gmail.com");
        Usuario usuario3 = new Usuario("ana.gutierrez@gmail.com");

        // THEN
        // son iguales (Equal) los que tienen el mismo e-mail.

        assertThat(usuario1).isEqualTo(usuario2);
        assertThat(usuario1).isNotEqualTo(usuario3);
    }


    @Test
    public void comprobarIgualdadUsuariosConId() {
        // GIVEN
        // Creadas tres usuarios con distintos e-mails y dos de ellos
        // con el mismo identificador,

        Usuario usuario1 = new Usuario("juan.gutierrez@gmail.com");
        Usuario usuario2 = new Usuario("pedro.gutierrez@gmail.com");
        Usuario usuario3 = new Usuario("ana.gutierrez@gmail.com");

        usuario1.setId(1L);
        usuario2.setId(2L);
        usuario3.setId(1L);

        // THEN
        // son iguales (Equal) los usuarios que tienen el mismo identificador.

        assertThat(usuario1).isEqualTo(usuario3);
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    //
    // Tests UsuarioRepository.
    // El código que trabaja con repositorios debe
    // estar en un entorno transactional, para que todas las peticiones
    // estén en la misma conexión a la base de datos, las entidades estén
    // conectadas y sea posible acceder a colecciones LAZY.
    //

    @Test
    @Transactional
    public void crearUsuarioBaseDatos() throws ParseException {
        // GIVEN
        // Un usuario nuevo creado sin identificador

        Usuario usuario = new Usuario("juan.gutierrez@gmail.com");
        usuario.setNombre("Juan Gutiérrez");
        usuario.setPassword("12345678");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        usuario.setFechaNacimiento(sdf.parse("1997-02-20"));

        // WHEN
        // se guarda en la base de datos

        usuarioRepository.save(usuario);

        // THEN
        // se actualiza el identificador del usuario,

        assertThat(usuario.getId()).isNotNull();

        // y con ese identificador se recupera de la base de datos el usuario con
        // los valores correctos de las propiedades.

        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(usuarioBD.getEmail()).isEqualTo("juan.gutierrez@gmail.com");
        assertThat(usuarioBD.getNombre()).isEqualTo("Juan Gutiérrez");
        assertThat(usuarioBD.getPassword()).isEqualTo("12345678");
        assertThat(usuarioBD.getFechaNacimiento()).isEqualTo(sdf.parse("1997-02-20"));
    }

    @Test
    @Transactional
    public void buscarUsuarioEnBaseDatos() {
        // GIVEN
        // Un usuario en la BD
        Usuario usuario = new Usuario("user@ua");
        usuario.setNombre("Usuario Ejemplo");
        usuarioRepository.save(usuario);
        Long usuarioId = usuario.getId();

        // WHEN
        // se recupera de la base de datos un usuario por su identificador,

        Usuario usuarioBD = usuarioRepository.findById(usuarioId).orElse(null);

        // THEN
        // se obtiene el usuario correcto y se recuperan sus propiedades.

        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getId()).isEqualTo(usuarioId);
        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Ejemplo");
    }

    @Test
    @Transactional
    public void buscarUsuarioPorEmail() {
        // GIVEN
        // Un usuario en la BD
        Usuario usuario = new Usuario("user@ua");
        usuario.setNombre("Usuario Ejemplo");
        usuarioRepository.save(usuario);

        // WHEN
        // buscamos al usuario por su correo electrónico,

        Usuario usuarioBD = usuarioRepository.findByEmail("user@ua").orElse(null);

        // THEN
        // se obtiene el usuario correcto.

        assertThat(usuarioBD.getNombre()).isEqualTo("Usuario Ejemplo");
    }

    @Test
    @Transactional
    public void existsByAdminTrue_ShouldReturnFalseWhenNoAdmin() {
        // GIVEN
        // No users in the database

        // WHEN
        boolean adminExists = usuarioRepository.existsByAdminTrue();

        // THEN
        assertThat(adminExists).isFalse();
    }

    @Test
    @Transactional
    public void existsByAdminTrue_ShouldReturnTrueWhenAdminExists() {
        // GIVEN
        Usuario adminUser = new Usuario("admin@ua");
        adminUser.setNombre("Admin User");
        adminUser.setPassword("adminpass");
        adminUser.setAdmin(true);
        usuarioRepository.save(adminUser);

        // WHEN
        boolean adminExists = usuarioRepository.existsByAdminTrue();

        // THEN
        assertThat(adminExists).isTrue();
    }

    @Test
    @Transactional
    public void findByAdminFalse_ShouldReturnOnlyNonAdminUsers() {
        // GIVEN
        // Create users: one admin and two non-admin
        Usuario adminUser = new Usuario("admin@ua");
        adminUser.setNombre("Admin User");
        adminUser.setPassword("adminpass");
        adminUser.setAdmin(true);
        usuarioRepository.save(adminUser);

        Usuario user1 = new Usuario("user1@ua");
        user1.setNombre("User One");
        user1.setPassword("userpass1");
        user1.setAdmin(false);
        usuarioRepository.save(user1);

        Usuario user2 = new Usuario("user2@ua");
        user2.setNombre("User Two");
        user2.setPassword("userpass2");
        user2.setAdmin(false);
        usuarioRepository.save(user2);

        // WHEN
        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> nonAdminUsersPage = usuarioRepository.findByAdminFalse(pageable);

        // THEN
        assertThat(nonAdminUsersPage.getTotalElements()).isEqualTo(2);
        assertThat(nonAdminUsersPage.getContent()).containsExactlyInAnyOrder(user1, user2);
        assertThat(nonAdminUsersPage.getContent()).doesNotContain(adminUser);
    }

    @Test
    @Transactional
    public void saveAndRetrieveBlockedStatus() {
        // GIVEN
        Usuario usuario = new Usuario("blocked@ua");
        usuario.setNombre("Blocked User");
        usuario.setPassword("blockedpass");
        usuario.setBlocked(true);
        usuarioRepository.save(usuario);

        // WHEN
        Usuario usuarioBD = usuarioRepository.findById(usuario.getId()).orElse(null);

        // THEN
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getBlocked()).isTrue();
    }




}