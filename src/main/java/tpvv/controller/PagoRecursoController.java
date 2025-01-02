package tpvv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tpvv.dto.PagoData;
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
    public String listarPagos(Model model) {

        return "listadoPagos";
    }

    @GetMapping("/allPagos")
    public String allPagos(Model model) {

        List<PagoData> pagos = pagoService.allPagos();
        return "listadoPagos";
    }
}
