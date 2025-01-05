package tpvv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.IncidenciaData;
import tpvv.dto.PagoRecursoData;
import tpvv.dto.UsuarioData;
import tpvv.repository.UsuarioRepository;
import tpvv.service.IncidenciaService;
import tpvv.service.PagoService;
import tpvv.service.UsuarioService;

import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/api")
public class IncidenciaController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ManagerUserSession managerUserSession;
    @Autowired
    private IncidenciaService incidenciaService;
    @Autowired
    private PagoService pagoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Long getUsuarioLogeadoId() {
        return managerUserSession.usuarioLogeado();
    }

    @GetMapping({"/comercio/incidencias", "/tecnico-or-admin/incidencias"})
    public String incidencias(Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        model.addAttribute("usuario", usuario);

        List<IncidenciaData> listIncidencias = incidenciaService.obtenerIncidenciasUsuario(getUsuarioLogeadoId());
        model.addAttribute("listIncidencias", listIncidencias);

        return "listadoIncidencias";
    }


    @GetMapping("/comercio/crear-incidencia")
    public String formularioCrearIncidenciaSinPago(
            @RequestParam(value="pago_id", required=false) Long pagoId,
            Model model) {

        IncidenciaData incidencia = new IncidenciaData();

        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        model.addAttribute("usuario", usuario);

        if(pagoId != null) {
            PagoRecursoData pago = pagoService.obtenerPagoPorId(pagoId);
            if(!Objects.equals(pago.getComercioData().getId(), usuario.getComercio().getId())) {
                return "redirect:/api/comercio/pagos/";
            }
            incidencia.setPago_id(pagoId);
        }

        model.addAttribute("incidencia", incidencia);
        return "crearIncidencia";
    }

    @PostMapping("/comercio/crear-incidencia")
    public String crearIncidencia(IncidenciaData incidencia, Model model) {
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        incidencia.setUsuarioComercio(usuario);
        IncidenciaData incidenciaNueva = incidenciaService.createIncidencia(incidencia);
        model.addAttribute("mensaje", "Incidencia creada correctamente");
        return "redirect:/api/comercio/incidencias";
    }
}


