package madstodolist.controller;

import madstodolist.dto.PagoData;
import madstodolist.model.Comercio;
import madstodolist.model.Pago;
import madstodolist.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

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
        pagoData.setImporte(importe);
        pagoData.setTicketExt(ticketId);
        model.addAttribute("pagoData", pagoData); // Nota: Asegúrate de usar "pagoData" como th:object
        return "paymentForm";
    }

    /**
     * Procesa el pago realizado.
     *
     * @param pagoData El formulario de pago enviado como DTO.
     * @param result   El resultado de la validación.
     * @param model    El modelo para la vista.
     * @param comercio El comercio autenticado.
     * @return La vista de confirmación o el formulario con errores.
     */
    @PostMapping("/realizar")
    public String realizarPago(@Valid @ModelAttribute("pagoData") PagoData pagoData,
                               BindingResult result,
                               Model model,
                               @ModelAttribute("comercio") Comercio comercio) {
        if (result.hasErrors()) {
            return "paymentForm";
        }

        // Procesar el pago
        Pago pago = pagoService.realizarPago(
                comercio,
                pagoData.getImporte(),
                pagoData.getTicketExt(),
                pagoData.getTarjeta()
        );

        // Mapear el objeto Pago a PagoData para la confirmación
        PagoData pagoConfirmacion = new PagoData();
        pagoConfirmacion.setId(pago.getId());
        pagoConfirmacion.setTicketExt(pago.getTicketExt());
        pagoConfirmacion.setFecha(pago.getFecha());
        pagoConfirmacion.setImporte(pago.getImporte());
        // Enmascarar los últimos 4 dígitos de la tarjeta
        String tarjetaEnmascarada = "**** **** **** " + pago.getTarjeta().substring(pago.getTarjeta().length() - 4);
        pagoConfirmacion.setTarjeta(tarjetaEnmascarada);
        pagoConfirmacion.setEstadoPago(pago.getEstado().getNombre());
        pagoConfirmacion.setComercioNombre(comercio.getNombre());

        model.addAttribute("pago", pagoConfirmacion);
        return "paymentConfirmation";
    }
}
