package tpvv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tpvv.authentication.ManagerUserSession;
import tpvv.controller.exception.UsuarioNoLogeadoException;
import tpvv.dto.ComercioData;
import tpvv.dto.PagoData;
import tpvv.dto.PagoRecursoData;
import tpvv.service.PagoService;

import java.util.List;

@Controller
public class PagoRecursoController {

    @Autowired
    ManagerUserSession managerUserSession;

    private Long devolverIdUsuarioLogeado() {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado == null)
            throw new UsuarioNoLogeadoException();
        return idUsuarioLogeado;
    }

    @Autowired
    PagoService pagoService;

    @GetMapping("/api/comercio/{id}/pagos")
    public String listarPagosComercio(@PathVariable(value="id") Long idUsuario,
                                      Model model) {
        Long idUsuarioLogeado = devolverIdUsuarioLogeado();

        if (!idUsuario.equals(idUsuarioLogeado))
            throw new UsuarioNoLogeadoException();

        ComercioData comercioData = pagoService.obtenerComercioDeUsuarioLogeado(idUsuarioLogeado);

        List<PagoRecursoData> pagos = pagoService.obtenerPagosDeUnComercio(comercioData.getId());

        for (PagoRecursoData pago : pagos) {
            if (pago.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                pago.setShownState("Aceptado");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("PEND")) {
                pago.setShownState("Pendiente");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("RECH")) {
                pago.setShownState("Rechazado");
            }

        }

        model.addAttribute("pagos", pagos);

        return "listadoPagosComercio";
    }

    @GetMapping("/api/admin/pagos")
    public String allPagos(Model model) {

        List<PagoRecursoData> pagos = pagoService.allPagos();

        for (PagoRecursoData pago : pagos) {
            if (pago.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                pago.setShownState("Aceptado");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("PEND")) {
                pago.setShownState("Pendiente");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("RECH")) {
                pago.setShownState("Rechazado");
            }

        }

        model.addAttribute("pagos", pagos);
        return "listadoPagos";
    }
}
