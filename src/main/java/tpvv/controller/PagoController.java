package tpvv.controller;

import tpvv.dto.PagoData;
import tpvv.model.Comercio;
import tpvv.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @ModelAttribute("comercio")
    public Comercio inicializarComercio() {
        // Aquí recuperas el comercio de la base de datos por su CIF, o lo creas por defecto
        return new Comercio("B12345678");
    }

    /**
     * Muestra el formulario de pago.
     *
     * @param importe  El importe del pago (parámetro GET).
     * @param ticketId El ID del ticket (parámetro GET).
     * @param model    El modelo para la vista.
     * @return La vista del formulario de pago.
     */
    @GetMapping("/form")
    public String mostrarFormularioPago(@RequestParam("importe") double importe,
                                        @RequestParam("idTicket") String ticketId,
                                        Model model) {
        PagoData pagoData = new PagoData();

        // MODIFICADO: Convertir el double a String
        pagoData.setImporte(Double.toString(importe));  // Se asigna como String

        pagoData.setTicketExt(ticketId);
        model.addAttribute("pagoData", pagoData);
        model.addAttribute("fullPage", true); // Plantilla completa
        return "paymentForm";
    }

}
