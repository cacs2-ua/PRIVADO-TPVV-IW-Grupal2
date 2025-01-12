package tpvv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tpvv.authentication.ManagerUserSession;
import tpvv.dto.IncidenciaData;
import tpvv.dto.MensajeData;
import tpvv.dto.PagoRecursoData;
import tpvv.dto.UsuarioData;
import tpvv.model.Mensaje;
import tpvv.repository.UsuarioRepository;
import tpvv.service.IncidenciaService;
import tpvv.service.PagoService;
import tpvv.service.UsuarioService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private static final int USUARIO_TECNICO = 2;
    private static final int USUARIO_COMERCIO = 3;
    private static final String ESTADO_NUEVA = "NUEVA";
    private static final String ESTADO_ASIGNADA = "ASIGN";
    private static final String ESTADO_RESUELTA = "RESUELTA";

    private Long getUsuarioLogeadoId() {
        return managerUserSession.usuarioLogeado();
    }

    @GetMapping({"/comercio/incidencias", "/tecnico-or-admin/incidencias"})
    public String incidencias(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) Long id,
                              @RequestParam(required = false) boolean asignado,
                              @RequestParam(required = false) String cif,
                              @RequestParam(required = false) String estado,

                              @RequestParam(required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,

                              @RequestParam(required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        Timestamp tsDesde = null;
        if (fechaDesde != null) {
            tsDesde = new Timestamp(fechaDesde.getTime());
        }

        Timestamp tsHasta = null;
        if (fechaHasta != null) {
            tsHasta = new Timestamp(fechaHasta.getTime());
        }
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        Long usuarioId = usuario.getId();



        // Obtenemos la página con paginación
        Page<IncidenciaData> pageResult = incidenciaService.filtrarIncidencias(
                page,
                4,
                id,
                usuarioId,
                asignado,
                cif,
                estado,
                tsDesde,
                tsHasta
        );
        String fechaDesdeStr = (fechaDesde != null) ? new SimpleDateFormat("yyyy-MM-dd").format(fechaDesde) : "";
        String fechaHastaStr = (fechaHasta != null) ? new SimpleDateFormat("yyyy-MM-dd").format(fechaHasta) : "";

        List<IncidenciaData> listIncidencias = pageResult.getContent();
        model.addAttribute("usuario", usuario);
        model.addAttribute("listIncidencias", listIncidencias);

        model.addAttribute("currentPage", pageResult.getNumber());
        model.addAttribute("totalPages", pageResult.getTotalPages());

        // Filtros
        model.addAttribute("idFilter", id);
        model.addAttribute("asignado", asignado);
        model.addAttribute("cif", cif);
        model.addAttribute("estadoFilter", estado);

        model.addAttribute("fechaDesdeStr", fechaDesdeStr);
        model.addAttribute("fechaHastaStr", fechaHastaStr);

        model.addAttribute("isUsuarioComercio", usuario.getTipoId() == USUARIO_COMERCIO);
        model.addAttribute("isUsuarioTecnico", usuario.getTipoId() == USUARIO_TECNICO);

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
        if(usuario.getTipoId() == USUARIO_COMERCIO) {
            return "redirect:/api/comercio/incidencia/" + incidenciaNueva.getId();
        }else{
            return "redirect:/api/tecnico-or-admin/incidencia/" + incidenciaNueva.getId();
        }
    }
    @PostMapping("/tecnico-or-admin/incidencia/asignarme")
    public String asignarmeIncidencia(Long incidenciaId, Model model) {

        boolean result = incidenciaService.asignarIncidencia(incidenciaId, getUsuarioLogeadoId());
        String mensaje = "";
        if(result) {
            mensaje = "Incidencia asignada correctamente";
        }
        else{
            mensaje = "Hubo un error, incidencia no asignada";
        }
        model.addAttribute("mensaje", mensaje);

        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        if(usuario.getTipoId() == USUARIO_COMERCIO) {
            return "redirect:/api/comercio/incidencia/" + incidenciaId;
        }else{
            return "redirect:/api/tecnico-or-admin/incidencia/" + incidenciaId;
        }
    }
    @GetMapping({"/comercio/incidencia/{id}", "/tecnico-or-admin/incidencia/{id}"})
    public String detallesIncidencia(@PathVariable(value = "id") Long idIncidencia,
                               Model model) {
        IncidenciaData incidencia = incidenciaService.obtenerIncidencia(idIncidencia);
        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        boolean canAddMessage = incidenciaService.canAddMessage(incidencia,usuario);
        boolean canValorar = usuario.getTipoId() == USUARIO_COMERCIO &&
                incidencia.getRazon_valoracion()==null &&
                incidencia.getUsuarioComercio() != null &&
                Objects.equals(incidencia.getEstado().getNombre(), ESTADO_RESUELTA) &&
                Objects.equals(incidencia.getUsuarioComercio().getId(), usuario.getId());
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("canAddMessage",canAddMessage);
        model.addAttribute("canValorar",canValorar);
        return "detallesIncidencia";
    }

    @PostMapping({"/tecnico-or-admin/incidencia/{id}/addMensaje","/comercio/incidencia/{id}/addMensaje"})
    public String addMensajeIncidencia(@PathVariable(value = "id") Long incidenciaId, String mensaje, Model model) {

        UsuarioData usuario = usuarioService.findById(getUsuarioLogeadoId());
        incidenciaService.addMensaje(incidenciaId,mensaje, getUsuarioLogeadoId());


        if(usuario.getTipoId() == USUARIO_COMERCIO) {
            return "redirect:/api/comercio/incidencia/" + incidenciaId;
        }else{
            return "redirect:/api/tecnico-or-admin/incidencia/" + incidenciaId;
        }
    }

    @PostMapping({"/tecnico-or-admin/incidencia/{id}/cerrar"})
    public String cerrarIncidencia(@PathVariable(value = "id") Long incidenciaId, Model model) {
        incidenciaService.cambiarEstado(incidenciaId,true, getUsuarioLogeadoId());
        return "redirect:/api/tecnico-or-admin/incidencia/" + incidenciaId;
    }

    @PostMapping({"/tecnico-or-admin/incidencia/{id}/abrir"})
    public String abirIncidencia(@PathVariable(value = "id") Long incidenciaId, Model model) {
        incidenciaService.cambiarEstado(incidenciaId,false, getUsuarioLogeadoId());
        return "redirect:/api/tecnico-or-admin/incidencia/" + incidenciaId;
    }

    @PostMapping({"/comercio/incidencia/{id}/valorar"})
    public String valorarIncidencia(@PathVariable(value = "id") Long incidenciaId, int valoracion,
                                    String razon_valoracion, Model model) {
        incidenciaService.addValoracion(incidenciaId,valoracion,razon_valoracion, getUsuarioLogeadoId());
        return "redirect:/api/comercio/incidencia/" + incidenciaId;
    }
}


