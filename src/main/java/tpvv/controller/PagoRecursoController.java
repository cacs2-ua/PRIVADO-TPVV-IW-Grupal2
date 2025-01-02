package tpvv.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PagoRecursoController {

    @GetMapping("/api/comercio/pagos")
    public String listarPagos(Model model) {

        return "listadoPagosComercio";
    }
}
