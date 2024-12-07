package madstodolist.controller;

import madstodolist.controller.exception.EquipoNotFoundException;
import madstodolist.controller.exception.UsuarioNoLogeadoException;
import madstodolist.dto.EquipoData;
import madstodolist.dto.UsuarioData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import madstodolist.authentication.ManagerUserSession;
import madstodolist.service.EquipoService;
import madstodolist.service.UsuarioService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EquipoController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EquipoService equipoService;

    @Autowired
    ManagerUserSession managerUserSession;


    private void comprobarUsuarioLogeado(Long idUsuario) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado == null || !idUsuario.equals(idUsuarioLogeado)) {
            throw new UsuarioNoLogeadoException();
        }
    }

    @GetMapping("/logeados/equipos")
    public String listadoEquipos(@RequestParam(value = "tipo", required = false) String tipoFiltrado,
                                 Model model) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();

        UsuarioData usuarioData = usuarioService.findById(idUsuarioLogeado);

        List<EquipoData> equipos;

        if (tipoFiltrado != null && !tipoFiltrado.equals("Todos")) {
            equipos = equipoService.filtrarEquiposPorTipo(tipoFiltrado);
        } else {
            equipos = equipoService.findAllOrdenadoPorNombre();
        }

        List<Boolean> equiposPertenecientes = equipoService.listaEquiposPerteneceUsuario(idUsuarioLogeado);

        model.addAttribute("usuario", usuarioData);
        model.addAttribute("equipos", equipos);
        model.addAttribute("equiposPertenecientes", equiposPertenecientes);
        model.addAttribute("tipoFiltrado", tipoFiltrado); // Asegúrate de pasar esto al modelo

        return "listaEquipos";
    }


    @GetMapping("/logeados/equipos/{equipo-id}/miembros")
    public String listadoMiembrosEquipo(@PathVariable(value="equipo-id") Long idEquipo,
                                        Model model) {

        EquipoData equipo = equipoService.recuperarEquipo(idEquipo);

        if (equipo == null) {
            throw new EquipoNotFoundException();
        }

        List<UsuarioData> usuarios = equipoService.usuariosEquipo(idEquipo);

        model.addAttribute("usuarios", usuarios);

        return "listaUsuariosDeUnEquipo";
    }

    @GetMapping("/logeados/equipos/nuevo-equipo")
    public String formNuevoEquipo(@ModelAttribute("equipoData") EquipoData equipoData,
                                  Model model) {
        // You can perform any necessary initialization here if needed
        return "formNuevoEquipo";
    }

    @PostMapping("/logeados/equipos/nuevo-equipo")
    public String nuevoEquipo(@ModelAttribute EquipoData equipoData,
                              Model model, RedirectAttributes flash,
                              HttpSession session) {

        equipoService.crearEquipo(equipoData.getNombre(), equipoData.getTipo());


        flash.addFlashAttribute("mensaje", "Equipo creado correctamente");


        return "redirect:/logeados/equipos";
    }

    @PostMapping("/logeados/equipos/{equipo-id}/agrega-usuario-logeado/{usuario-id}")
    public String agregarUsuarioLogeadoEnEquipo(@PathVariable(value="equipo-id") Long idEquipo,
                                                @PathVariable(value="usuario-id") Long idUsuario,
                                                @ModelAttribute EquipoData equipoData,
                                                Model model, RedirectAttributes flash,
                                                HttpSession session) {
        EquipoData equipo = equipoService.recuperarEquipo(idEquipo);

        if (equipo == null) {
            throw new EquipoNotFoundException();
        }

        equipoService.añadirUsuarioAEquipo(idEquipo, idUsuario);
        return "redirect:/logeados/equipos";
    }

    @PostMapping("/logeados/equipos/{equipo-id}/elimina-usuario-logeado/{usuario-id}")
    public String eliminaUsuarioLogeadoDeEquipo(@PathVariable(value="equipo-id") Long idEquipo,
                                                @PathVariable(value="usuario-id") Long idUsuario,
                                                @ModelAttribute EquipoData equipoData,
                                                Model model, RedirectAttributes flash,
                                                HttpSession session) {
        EquipoData equipo = equipoService.recuperarEquipo(idEquipo);

        if (equipo == null) {
            throw new EquipoNotFoundException();
        }


        equipoService.borrarUsuarioDelEquipo(idEquipo, idUsuario);
        return "redirect:/logeados/equipos";
    }

    @GetMapping("/admin/auth/equipos/{id}/editar")
    public String formEditarEquipo(@PathVariable(value="id") Long idEquipo,
                                  @ModelAttribute("equipoData") EquipoData equipoData,
                                  Model model) {
        EquipoData equipo = equipoService.recuperarEquipo(idEquipo);

        if (equipo == null) {
            throw new EquipoNotFoundException();
        }

        model.addAttribute("equipo", equipo);
        return "formEditarEquipo";
    }

    @PostMapping("/admin/auth/equipos/{id}/editar")
    public String editarEquipo(@PathVariable(value="id") Long idEquipo,
                               @ModelAttribute EquipoData equipoData,
                               Model model, RedirectAttributes flash,
                               HttpSession session) {

        EquipoData equipo = equipoService.recuperarEquipo(idEquipo);

        if (equipo == null) {
            throw new EquipoNotFoundException();
        }

        equipoService.modificarNombreEquipo(idEquipo, equipoData.getNombre());
        equipoService.modificarTipoEquipo(idEquipo, equipoData.getTipo());


        flash.addFlashAttribute("mensaje", "Equipo Modificado correctamente");

        return "redirect:/logeados/equipos";
    }


    @PostMapping("/admin/auth/equipos/{id}/borrar")
    public String borrarEquipo(@PathVariable(value="id") Long idEquipo,
                               @ModelAttribute EquipoData equipoData,
                               Model model, RedirectAttributes flash,
                               HttpSession session) {
        EquipoData equipo = equipoService.recuperarEquipo(idEquipo);

        if (equipo == null) {
            throw new EquipoNotFoundException();
        }

        equipoService.borrarEquipo(equipo.getId());
        flash.addFlashAttribute("mensaje", "Equipo borrado correctamente");
        return "redirect:/logeados/equipos";
    }



}
