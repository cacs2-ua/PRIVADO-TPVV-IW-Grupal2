package tpvv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.ComercioData;
import tpvv.dto.PaisData;
import tpvv.dto.PersonaContactoData;
import tpvv.dto.UsuarioData;
import tpvv.model.Comercio;
import tpvv.model.PersonaContacto;
import tpvv.repository.PaisRepository;
import tpvv.service.ComercioService;
import tpvv.service.PaisService;
import tpvv.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ComercioController {

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


    @GetMapping("/api/admin/crearcomercio")
    public String formularioComercio(Model model) {

        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        ComercioData comercio = new ComercioData();
        PersonaContactoData personaContacto = new PersonaContactoData();
        List<PaisData> paises = paisService.findAll();

        //model.addAttribute("usuario", usuario);
        model.addAttribute("comercio", comercio);
        model.addAttribute("paises", paises);
        model.addAttribute("personaContacto", personaContacto);


        return "registrarComercio";

    }

    @PostMapping("/creacomercio")
    public String registrarComercio(ComercioData comercio, PersonaContactoData personaContacto, Model model) {
        ComercioData nuevoComercio = comercioService.crearComercio(comercio);
        if (personaContacto.getNombreContacto() != null) {
            PersonaContactoData contacto = comercioService.crearPersonaContacto(personaContacto);
            comercioService.asignarPersonaDeContactoAComercio(nuevoComercio.getId(), contacto.getId());
        }
        model.addAttribute("mensaje", "Comercio registrado con éxito");
        return "redirect:/api/admin/crearcomercio";  // redirigir al formulario o a una página de confirmación
    }

    @GetMapping("/api/admin/comercios")
    public String listarComercio(Model model) {
        List<ComercioData> comercios = comercioService.recuperarTodosLosComercios();
        model.addAttribute("comercios", comercios);


        return "listadoComercio";

    }
}
