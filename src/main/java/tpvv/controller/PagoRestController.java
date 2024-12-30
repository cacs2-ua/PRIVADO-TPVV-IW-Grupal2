package tpvv.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpvv.dto.PagoCompletoRequest;
import tpvv.service.PagoService;

@RestController
@RequestMapping("/pago")
public class PagoRestController {

    @Autowired
    private PagoService pagoService;

    /**
     * Procesa el pago realizado, recibiendo en un solo body:
     * - PagoData (importe, ticketExt, fecha, etc.)
     * - TarjetaPagoData (numeroTarjeta, cvc, fechaCaducidad, nombre)
     */
    @PostMapping("/realizar")
    public ResponseEntity<String> realizarPago(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @Valid @RequestBody PagoCompletoRequest request) {

        // Si la cabecera viene nula o en blanco, manejarlo según tu lógica
        String apiKey = (authorizationHeader != null) ? authorizationHeader.trim() : "";

        try {
            String respuesta = pagoService.procesarPago(request, apiKey);
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error 404");
        }
    }
}
