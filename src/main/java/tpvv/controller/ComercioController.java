package tpvv.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.ComercioData;
import tpvv.dto.PaisData;
import tpvv.dto.PersonaContactoData;
import tpvv.dto.UsuarioData;
import org.springframework.data.domain.Page;



import tpvv.service.ComercioService;
import tpvv.service.PaisService;
import tpvv.service.UsuarioService;

import java.time.LocalDate;
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
        return "redirect:/api/admin/crearcomercio";
    }

    /*
    @GetMapping("/api/admin/comercios")
    public String listarComercio(Model model) {
        List<ComercioData> comercios = comercioService.recuperarTodosLosComercios();
        model.addAttribute("comercios", comercios);


        return "listadoComercio";

    }



    @GetMapping("/api/admin/comercios")
    public String getComercios(@RequestParam(defaultValue = "0") int page, Model model) {
        List<ComercioData> comerciosData = comercioService.recuperarTodosLosComercios();
        Page<ComercioData> comerciosPage = comercioService.recuperarComerciosPaginados(comerciosData, page, 8);
        int totalPages = comerciosPage.getTotalPages();
        List<PaisData> paises = paisService.findAll();

        model.addAttribute("paises", paises);
        model.addAttribute("comercios", comerciosPage.getContent());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "listadoComercio";
    }
    /
     */
    @GetMapping("/api/admin/comercios")
    public String getComercios(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String cif,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        List<ComercioData> todosLosComercios = comercioService.recuperarTodosLosComercios();
        List<ComercioData> comerciosFiltrados = comercioService.filtrarComercios(todosLosComercios, id, nombre, cif, pais, fechaDesde, fechaHasta);

        Page<ComercioData> comerciosPage = comercioService.recuperarComerciosPaginados(comerciosFiltrados, page, 8);
        int totalPages = comerciosPage.getTotalPages();

        List<PaisData> paises = paisService.findAll();

        // Añadir atributos al modelo
        model.addAttribute("paises", paises);
        model.addAttribute("comercios", comerciosPage.getContent());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "listadoComercio";
    }

    @PostMapping("/api/admin/comercios/activar/{id}")
    @ResponseBody
    public String activarComercio(@PathVariable(value="id") Long idComercio, RedirectAttributes flash, HttpSession session) {
        comercioService.modificarEstadoComercio(idComercio, true);
        return "";
    }

    @PostMapping("/api/admin/comercios/estado/{id}")
    public String desactivarComercio(@PathVariable(value="id") Long idComercio, RedirectAttributes flash, HttpSession session) {
        ComercioData comercio = comercioService.recuperarComercio(idComercio);
        comercioService.modificarEstadoComercio(idComercio, !comercio.getActivo());
        return "redirect:/api/admin/comercios";
    }
}
