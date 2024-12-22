package tpvv.controller;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginData", new LoginData());
        return "formLogin";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginData loginData, Model model, HttpSession session) {

        // Llamada al servicio para comprobar si el login es correcto
        UsuarioService.LoginStatus loginStatus = usuarioService.login(loginData.getEmail(), loginData.getPassword());

        if (loginStatus == UsuarioService.LoginStatus.LOGIN_OK) {
            UsuarioData usuario = usuarioService.findByEmail(loginData.getEmail());

            managerUserSession.logearUsuario(usuario.getId());

            model.addAttribute("usuario", usuario); // Add user to model
            return "redirect:/usuarios/" + usuario.getId() + "/tareas";
        } else if (loginStatus == UsuarioService.LoginStatus.USER_NOT_FOUND) {
            model.addAttribute("error", "No existe usuario");
            return "formLogin";
        } else if (loginStatus == UsuarioService.LoginStatus.ERROR_PASSWORD) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "formLogin";
        } else if (loginStatus == UsuarioService.LoginStatus.USER_BLOCKED) {
            model.addAttribute("error", "Su cuenta ha sido bloqueada por el administrador");
            return "formLogin";
        }
        return "formLogin";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        RegistroData registroData = new RegistroData();
        model.addAttribute("registroData", registroData);

        return "formRegistro";
    }

    @PostMapping("/registro")
    public String registroSubmit(@Valid RegistroData registroData, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "formRegistro";
        }

        if (usuarioService.findByEmail(registroData.getEmail()) != null) {
            model.addAttribute("registroData", registroData);
            model.addAttribute("error", "El usuario " + registroData.getEmail() + " ya existe");
            return "formRegistro";
        }

        try {
            UsuarioData nuevoUsuario = usuarioService.registrar(registroData);
            return "redirect:/login";

        } catch (UsuarioServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "formRegistro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        managerUserSession.logout();
        return "redirect:/login";
    }
}

