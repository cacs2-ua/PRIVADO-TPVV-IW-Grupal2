package tpvv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tpvv.dto.PagoData;
import tpvv.dto.PagoRecursoData;
import tpvv.service.PagoService;

import java.util.List;

@Controller
public class PagoRecursoController {

    @Autowired
    PagoService pagoService;

    @GetMapping("/api/comercio/pagos")
    public String listarPagosComercio(Model model) {

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
