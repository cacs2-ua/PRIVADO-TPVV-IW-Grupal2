// EquipoWebTest.java

package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.EquipoData;
import madstodolist.dto.UsuarioData;
import madstodolist.dto.RegistroData;
import madstodolist.service.EquipoService;
import madstodolist.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/clean-db.sql")
public class CuentaWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private ManagerUserSession managerUserSession;

    /**
     * Método para inicializar los datos de prueba en la BD.
     * Crea un usuario y dos equipos asociados a ese usuario.
     */
    Map<String, Long> addUsuarioEquiposBD() {
        // Añadimos un usuario a la base de datos
        RegistroData registroData = new RegistroData();
        registroData.setNombre("usuario");
        registroData.setEmail("usuario@ua.com");
        registroData.setPassword("password");
        UsuarioData usuario = usuarioService.registrar(registroData);

        RegistroData registroDataAdmin = new RegistroData();
        registroDataAdmin.setEmail("admin@admin.com");
        registroDataAdmin.setPassword("admin");
        registroDataAdmin.setAdmin(true);
        UsuarioData usuarioAdmin = usuarioService.registrar(registroDataAdmin);

        // Añadimos dos equipos asociados a ese usuario
        EquipoData equipo1 = equipoService.crearEquipo("Equipo Alpha");
        EquipoData equipo2 = equipoService.crearEquipo("Equipo Beta");

        // Asignamos el usuario a ambos equipos
        equipoService.añadirUsuarioAEquipo(equipo1.getId(), usuario.getId());
        equipoService.añadirUsuarioAEquipo(equipo2.getId(), usuario.getId());

        Long equipoNoExistenteId = 999L;

        // Devolvemos los IDs del usuario y los equipos
        Map<String, Long> ids = new HashMap<>();
        ids.put("usuarioId", usuario.getId());
        ids.put("usuarioAdmin", usuarioAdmin.getId());
        ids.put("equipo1Id", equipo1.getId());
        ids.put("equipo2Id", equipo2.getId());
        ids.put("equipoNoExistenteId", equipoNoExistenteId);

        return ids;
    }

    @Test
    public void cambiarContraseniaTest() throws Exception {
        Long usuarioId = addUsuarioEquiposBD().get("usuarioId");
        String contraseniaNueva = "hola1234";
        when(managerUserSession.usuarioLogeado()).thenReturn(usuarioId);

        String urlPeticion = "/logeados/cuenta/" + usuarioId + "/cambiarcontrasena";
        String urlRedirect = "/logeados/cuenta/" + usuarioId;

        this.mockMvc.perform(post(urlPeticion)
                        .with(csrf())
                                .with(user("usuario@ua.com"))
                        .param("contrasena", contraseniaNueva))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(urlRedirect));

        assertThat(usuarioService.findById(usuarioId).getPassword()).isEqualTo(contraseniaNueva);
    }

    @Test
    public void cambiarNombreTest() throws Exception {
        Long usuarioId = addUsuarioEquiposBD().get("usuarioId");
        String nombreNuevo = "user";
        when(managerUserSession.usuarioLogeado()).thenReturn(usuarioId);

        String urlPeticion = "/logeados/cuenta/" + usuarioId + "/cambiarnombre";
        String urlRedirect = "/logeados/cuenta/" + usuarioId;

        this.mockMvc.perform(post(urlPeticion)
                        .with(csrf())
                                .with(user("usuario@ua.com"))
                        .param("nombre", nombreNuevo))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(urlRedirect));

        assertThat(usuarioService.findById(usuarioId).getNombre()).isEqualTo(nombreNuevo);
    }

}
