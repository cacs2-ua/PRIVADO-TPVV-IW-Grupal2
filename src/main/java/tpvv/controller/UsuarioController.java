package tpvv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.UsuarioData;
import tpvv.service.ComercioService;
import tpvv.service.PaisService;
import tpvv.service.UsuarioService;

@Controller
public class UsuarioController {


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
}
