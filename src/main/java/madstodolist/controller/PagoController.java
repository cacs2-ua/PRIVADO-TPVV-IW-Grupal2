package madstodolist.controller;

import madstodolist.model.Comercio;
import madstodolist.model.Pago;
import madstodolist.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import java.util.Optional;

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
        model.addAttribute("importe", importe);
        model.addAttribute("idTicket", ticketId);
        model.addAttribute("pagoForm", new PagoForm(importe, ticketId));
        return "paymentForm";
    }

    /**
     * Procesa el pago realizado.
     *
     * @param pagoForm El formulario de pago enviado.
     * @param result   El resultado de la validación.
     * @param model    El modelo para la vista.
     * @param comercio El comercio autenticado.
     * @return La vista de confirmación o el formulario con errores.
     */
    @PostMapping("/realizar")
    public String realizarPago(@Valid @ModelAttribute("pagoForm") PagoForm pagoForm,
                               BindingResult result,
                               Model model,
                               @ModelAttribute("comercio") Comercio comercio) {
        if (result.hasErrors()) {
            return "paymentForm";
        }

        // Procesar el pago
        Pago pago = pagoService.realizarPago(comercio, pagoForm.getImporte(), pagoForm.getIdTicket(), pagoForm.getTarjeta());

        model.addAttribute("pago", pago);
        return "paymentConfirmation";
    }

    /**
     * Clase interna para manejar el formulario de pago.
     */
    public static class PagoForm {
        private double importe;
        private String idTicket;
        private String tarjeta;

        public PagoForm() {}

        public PagoForm(double importe, String idTicket) {
            this.importe = importe;
            this.idTicket = idTicket;
        }

        // Getters y Setters

        public double getImporte() {
            return importe;
        }

        public void setImporte(double importe) {
            this.importe = importe;
        }

        public String getIdTicket() {
            return idTicket;
        }

        public void setIdTicket(String idTicket) {
            this.idTicket = idTicket;
        }

        public String getTarjeta() {
            return tarjeta;
        }

        public void setTarjeta(String tarjeta) {
            this.tarjeta = tarjeta;
        }
    }
}
