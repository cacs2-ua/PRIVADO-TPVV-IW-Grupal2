package tpvv.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.LoginData;
import tpvv.dto.RegistroData;
import tpvv.dto.UsuarioData;
import tpvv.service.UsuarioService;
import tpvv.service.exception.UsuarioServiceException;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ManagerUserSession managerUserSession;

    private Long getUsuarioLogeadoId() {
        return managerUserSession.usuarioLogeado();
    }

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        if (getUsuarioLogeadoId() != null) {
            return "redirect:/api/general/bienvenida";
        }

        model.addAttribute("loginData", new LoginData());
        return "formLogin"; // Plantilla adaptada con thymeleaf
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginData loginData, Model model) {
        UsuarioService.LoginStatus loginStatus = usuarioService.login(
                loginData.getEmail(),
                loginData.getContrasenya()
        );

        if (loginStatus == UsuarioService.LoginStatus.LOGIN_OK) {
            UsuarioData usuario = usuarioService.findByEmail(loginData.getEmail());
            managerUserSession.logearUsuario(usuario.getId());

            if (usuario.getTipoId() == 1){
                return "redirect:/api/admin/dashboard";
            }
            if (usuario.getTipoId() == 2){
                return "redirect:/api/tecnico/dashboard";
            }
            if (usuario.getTipoId() == 3){
                return "redirect:/api/comercio/dashboard";
            }
            // Redirigir a la página de bienvenida
            return "redirect:/api/general/bienvenida";

        } else if (loginStatus == UsuarioService.LoginStatus.USER_DISABLED) {
            model.addAttribute("error", "No puedes iniciar sesión. Tu usuario está deshabilitado");
            return "formLogin";
        } else {
            model.addAttribute("error", "Ha habido algún error al iniciar sesión");
            return "formLogin";
        }

    }

    // Nueva ruta para la página de bienvenida
    @GetMapping("/api/general/bienvenida")
    public String bienvenida(Model model) {
        // Aquí podrías pasar datos adicionales al modelo si lo deseas
        return "debug/bienvenida";
    }


    @GetMapping("/logout")
    public String logout() {
        managerUserSession.logout();
        return "redirect:/login";
    }
}
