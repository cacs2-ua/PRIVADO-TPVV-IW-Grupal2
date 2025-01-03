package tpvv.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.ComercioData;
import tpvv.dto.PersonaContactoData;
import tpvv.dto.UsuarioData;
import tpvv.service.ComercioService;
import tpvv.service.PaisService;
import tpvv.service.UsuarioService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UsuariosController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    private PaisService paisService;
    @Autowired
    private ComercioService comercioService;

    private Long getUsuarioLogeadoId() {
        return managerUserSession.usuarioLogeado();
    }

    @GetMapping("/api/tecnico/mis-datos")
    public String perfilTecnico(Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        model.addAttribute("usuario", usuario);

        return "perfilTecnico";
    }

    @GetMapping("/api/admin/usuarios")
    public String listadoUsuarios(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long comercio,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        List<ComercioData> comercios = comercioService.recuperarTodosLosComercios();
        List<UsuarioData> todosLosUsuarios = usuarioService.findAll();
        List<UsuarioData> usuariosFiltrados = usuarioService.filtrarUsuarios(todosLosUsuarios, id, comercio, estado, fechaDesde, fechaHasta);

        Page<UsuarioData> usuariosPage = usuarioService.recuperarUsuariosPaginados(usuariosFiltrados, page, 8);
        int totalPages = usuariosPage.getTotalPages();

        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("comercios", comercios);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "listadoUsuario";
    }

    @PostMapping("/api/admin/usuarios/estado/{id}")
    public String desactivarComercio(@PathVariable(value="id") Long idUsuario, RedirectAttributes flash, HttpSession session) {
        UsuarioData usuario = usuarioService.findById(idUsuario);
        usuarioService.borradoUsuarioLogico(idUsuario, !usuario.getActivo());
        return "redirect:/api/admin/usuarios";
    }
}
