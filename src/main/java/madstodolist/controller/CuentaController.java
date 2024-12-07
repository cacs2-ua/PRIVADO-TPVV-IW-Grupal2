package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CuentaController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ManagerUserSession managerUserSession;

    @GetMapping("/logeados/cuenta/{id}")
    public String cuenta(@PathVariable(value="id") Long idUsuario, Model model) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado != null) {
            UsuarioData usuario = usuarioService.findById(idUsuarioLogeado);
            model.addAttribute("usuario", usuario);
        }
        return "cuenta";
    }

    @PostMapping("/logeados/cuenta/{id}/cambiarcontrasena")
    public String cambiarContrasena(@PathVariable(value="id") Long idUsuario, @RequestParam String contrasena, Model model, RedirectAttributes flash, HttpSession session) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado != null) {
            usuarioService.cambiarContrasena(idUsuarioLogeado, contrasena);
        }
        return "redirect:/logeados/cuenta/" + idUsuarioLogeado;
    }

    @PostMapping("/logeados/cuenta/{id}/cambiarnombre")
    public String cambiarNombre(@PathVariable(value="id") Long idUsuario, @RequestParam String nombre, Model model, RedirectAttributes flash, HttpSession session) {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado != null) {
            usuarioService.cambiarNombre(idUsuarioLogeado, nombre);
        }
        return "redirect:/logeados/cuenta/" + idUsuarioLogeado;
    }
}
