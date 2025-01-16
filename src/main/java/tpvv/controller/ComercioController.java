package tpvv.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.*;
import org.springframework.data.domain.Page;


import tpvv.repository.IncidenciaRepository;
import tpvv.repository.PagoRepository;
import tpvv.service.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    @Autowired
    private PagoService pagoService;
    @Autowired
    private IncidenciaService incidenciaService;
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    private Long getUsuarioLogeadoId() {
        return managerUserSession.usuarioLogeado();
    }


    @GetMapping("/api/comercio/dashboard")
    public String dashboardComercio(Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        Long id_comercio = usuarioService.findComercio(usuario.getId());
        List<UsuarioData> usuariosComercio = usuarioService.findAllByIdComercio(id_comercio);
        List<Long> usuariosIds = usuariosComercio.stream().map(UsuarioData::getId).toList();

        LocalDate haceCincoYears = LocalDate.now().minusYears(5);
        Date haceCincoYearsDate = Date.from(haceCincoYears.atStartOfDay(ZoneId.systemDefault()).toInstant());


        Long pagos = pagoRepository.countPagosByComercioAndFechaDesde(id_comercio, haceCincoYearsDate);
        Long incidencias = incidenciaRepository.countIncidenciasByUsuarioIdsAndFechaDesde(usuariosIds, haceCincoYearsDate);
        Long incidenciasResueltas = incidenciaRepository.countIncidenciasByUsuarioIdsFechaDesdeAndEstado(usuariosIds, haceCincoYearsDate, 3);


        model.addAttribute("pagos", pagos);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("incidenciasResueltas", incidenciasResueltas);

        return "dashComercios";

    }

    @GetMapping("/api/tecnico/dashboard")
    public String dashboardTecnico(Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());


        LocalDate haceCincoYears = LocalDate.now().minusYears(5);
        Date haceCincoYearsDate = Date.from(haceCincoYears.atStartOfDay(ZoneId.systemDefault()).toInstant());



        Long incidenciasAsignadas = incidenciaRepository.countIncidenciasByUsuarioTecnicoAndFechaDesde(usuario.getId(), haceCincoYearsDate);
        Long incidenciasAsignadasResueltas = incidenciaRepository.countIncidenciasByUsuarioTecnicoAndFechaDesdeAndEstado(usuario.getId(), haceCincoYearsDate, 3);


        model.addAttribute("incidenciasAsignadas", incidenciasAsignadas);
        model.addAttribute("incidenciasAsignadasResueltas", incidenciasAsignadasResueltas);

        return "dashTecnicos";

    }

    @GetMapping("/api/admin/dashboard")
    public String dashboardAdmin(Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());


        LocalDate haceCincoYears = LocalDate.now().minusYears(5);
        Date haceCincoYearsDate = Date.from(haceCincoYears.atStartOfDay(ZoneId.systemDefault()).toInstant());



        Long pagos = pagoRepository.countPagosByFechaDesde(haceCincoYearsDate);
        Long incidencias = incidenciaRepository.countIncidenciasByFechaDesde(haceCincoYearsDate);
        Long incidenciasResueltas = incidenciaRepository.countIncidenciasByFechaDesdeAndEstado(haceCincoYearsDate, 3);

        model.addAttribute("pagos", pagos);
        model.addAttribute("incidencias", incidencias);
        model.addAttribute("incidenciasResueltas", incidenciasResueltas);

        return "dashAdmins";

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

    @PostMapping("/api/admin/crearcomercio")
    public String registrarComercio(ComercioData comercio, PersonaContactoData personaContacto, Model model) {
        ComercioData nuevoComercio = comercioService.crearComercio(comercio);
        if (personaContacto.getNombreContacto() != null && personaContacto.getNombreContacto() != "") {
            PersonaContactoData contacto = comercioService.crearPersonaContacto(personaContacto);
            comercioService.asignarPersonaDeContactoAComercio(nuevoComercio.getId(), contacto.getId());
        }
        model.addAttribute("mensaje", "Comercio registrado con éxito");
        return "redirect:/api/admin/crearcomercio";
    }

    @GetMapping("/api/tecnico-or-admin/comercios")
    public String getComercios(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String cif,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        List<ComercioData> todosLosComercios = comercioService.recuperarTodosLosComercios();
        List<ComercioData> comerciosFiltrados = comercioService.filtrarComercios(todosLosComercios, id, nombre, cif, pais, fechaDesde, fechaHasta);

        Page<ComercioData> comerciosPage = comercioService.recuperarComerciosPaginados(comerciosFiltrados, page, 4);
        int totalPages = comerciosPage.getTotalPages();

        List<PaisData> paises = paisService.findAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("paises", paises);
        model.addAttribute("comercios", comerciosPage.getContent());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        model.addAttribute("idFilter", id);
        model.addAttribute("nombreFilter", nombre);
        model.addAttribute("cifFilter", cif);
        model.addAttribute("paisFilter", pais);
        model.addAttribute("fechaDesdeStr", fechaDesde);
        model.addAttribute("fechaHastaStr", fechaHasta);

        return "listadoComercio";
    }


    @PostMapping("/api/tecnico-or-admin/comercios/estado/{id}")
    public String desactivarComercio(@PathVariable(value="id") Long idComercio, RedirectAttributes flash, HttpSession session) {
        ComercioData comercio = comercioService.recuperarComercio(idComercio);
        comercioService.modificarEstadoComercio(idComercio, !comercio.getActivo());
        return "redirect:/api/tecnico-or-admin/comercios";
    }

    @GetMapping("/api/tecnico-or-admin/comercios/detalles/{id}")
    public String detallesComercio(@PathVariable(value="id") Long idComercio,Model model) {

        ComercioData comercio = comercioService.recuperarComercio(idComercio);
        PersonaContactoData personaContacto = comercioService.recuperarPersonaContactoByComercioId(idComercio);
        List<UsuarioData> usuarios = usuarioService.findAllByIdComercio(idComercio);

        model.addAttribute("comercio", comercio);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("personaContacto", personaContacto);


        return "detallesComercio";

    }

    @PostMapping("/api/tecnico-or-admin/comercios/regenerar-api/{id}")
    public String regenerarAPIKEYComercio(@PathVariable(value="id") Long idComercio, RedirectAttributes flash, HttpSession session) {
        comercioService.regenerarAPIKeyComercio(idComercio);
        return "redirect:/api/tecnico-or-admin/comercios/detalles/" + idComercio;
    }

    @PostMapping("/api/tecnico-or-admin/comercios/actualizar-url/{id}")
    public String actualizarURL(@PathVariable("id") Long idComercio,
                                @RequestParam("url") String url,
                                RedirectAttributes flash) {
        comercioService.actualizarURLComercio(idComercio, url);
        return "redirect:/api/tecnico-or-admin/comercios/detalles/" + idComercio;
    }

    @GetMapping("/api/comercio/mis-datos")
    public String perfilComercio(Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        Long id_comercio = usuarioService.findComercio(usuario.getId());
        ComercioData comercio = comercioService.recuperarComercio(id_comercio);
        PersonaContactoData personaContacto = comercioService.recuperarPersonaContactoByComercioId(comercio.getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("comercio", comercio);
        model.addAttribute("personaContacto", personaContacto);


        return "perfilComercio";
    }

    @PostMapping("/api/comercio/mis-datos/regenerar-api/{id}")
    public String regenerarAPIKEYComercioUsuario(@PathVariable(value="id") Long idComercio, RedirectAttributes flash, HttpSession session) {
        comercioService.regenerarAPIKeyComercio(idComercio);
        return "redirect:/api/comercio/mis-datos";
    }

    @PostMapping("/api/comercio/mis-datos/actualizar-url/{id}")
    public String actualizarURLComercioUsuario(@PathVariable("id") Long idComercio,
                                @RequestParam("url") String url,
                                RedirectAttributes flash) {
        comercioService.actualizarURLComercio(idComercio, url);
        return "redirect:/api/comercio/mis-datos";
    }
}
