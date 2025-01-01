package tpvv.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpvv.dto.PagoCompletoRequest;
import tpvv.dto.PedidoCompletoRequest;
import tpvv.dto.PagoData;
import tpvv.dto.TarjetaPagoData;
import tpvv.service.PagoService;

// NUEVO: Importaciones adicionales para WebClient
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/pago")
public class PagoRestController {

    private static final Logger log = LoggerFactory.getLogger(PagoRestController.class);

    @Autowired
    private PagoService pagoService;

    // Inyectamos el WebClient (definido en WebClientConfig)
    @Autowired
    private WebClient webClient;

    /**
     * Procesa el pago realizado, recibiendo en un solo body:
     * - PagoData (importe, ticketExt, fecha, etc.)
     * - TarjetaPagoData (numeroTarjeta, cvc, fechaCaducidad, nombre)
     */
    @PostMapping("/realizar")
    public ResponseEntity<String> realizarPago(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @Valid @RequestBody PagoCompletoRequest request) {

        String apiKey = (authorizationHeader != null) ? authorizationHeader.trim() : "";

        // ========================
        //  Validaciones
        // ========================
        PagoData pagoData = request.getPagoData();
        TarjetaPagoData tarjetaData = request.getTarjetaPagoData();

        StringBuilder errorMsg = new StringBuilder();

        if (pagoData == null) {
            errorMsg.append("PagoData no puede ser nulo. ");
        } else {
            if (pagoData.getImporte() == null || pagoData.getImporte().isBlank()) {
                errorMsg.append("El importe no puede estar vacío. ");
            }
            if (pagoData.getTicketExt() == null || pagoData.getTicketExt().isBlank()) {
                errorMsg.append("El ticketExt no puede estar vacío. ");
            }
            if (pagoData.getFecha() == null || pagoData.getFecha().isBlank()) {
                errorMsg.append("La fecha no puede estar vacía. ");
            }
        }

        if (tarjetaData == null) {
            errorMsg.append("TarjetaPagoData no puede ser nulo. ");
        } else {
            if (tarjetaData.getNombre() == null || tarjetaData.getNombre().isBlank()) {
                errorMsg.append("El nombre del titular de la tarjeta no puede estar vacío. ");
            }
            if (tarjetaData.getNumeroTarjeta() == null || tarjetaData.getNumeroTarjeta().isBlank()) {
                errorMsg.append("El número de tarjeta no puede estar vacío. ");
            } else if (!tarjetaData.getNumeroTarjeta().matches("^\\d{16}$")) {
                errorMsg.append("El número de tarjeta debe tener exactamente 16 dígitos. ");
            }

            if (tarjetaData.getFechaCaducidad() == null || tarjetaData.getFechaCaducidad().isBlank()) {
                errorMsg.append("La fecha de caducidad no puede estar vacía. ");
            } else if (!tarjetaData.getFechaCaducidad().matches("^(0[1-9]|1[0-2])\\/\\d{2}$")) {
                errorMsg.append("La fecha de caducidad debe tener el formato mm/yy. ");
            }

            if (tarjetaData.getCvc() == null || tarjetaData.getCvc().isBlank()) {
                errorMsg.append("El código de seguridad no puede estar vacío. ");
            } else if (!tarjetaData.getCvc().matches("^\\d{3}$")) {
                errorMsg.append("El CVC debe tener exactamente 3 dígitos. ");
            }
        }

        // ==============================
        // NUEVO: En lugar de 400, devolvemos 200 con "ERROR|..."
        // ==============================
        if (errorMsg.length() > 0) {
            // Partes NUEVAS:
            // En vez de BAD_REQUEST, devolvemos OK(200).
            // Comenzamos el body con "ERROR|" para que el cliente lo detecte.
            return ResponseEntity.ok("ERROR|" + errorMsg.toString());
        }

        // ==============================
        // Si no hubo errores, procesamos:
        // ==============================
        try {
            String urlBack = pagoService.obtenerUrlBack(apiKey);
            PedidoCompletoRequest pedidoCompletoRequest = pagoService.procesarPago(request, apiKey);

            // Llamada POST al proyecto cliente, enviando pedidoCompletoRequest
            Mono<String> response = webClient.post()
                    .uri(urlBack)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(pedidoCompletoRequest))
                    .retrieve()
                    .bodyToMono(String.class);

            String storeResponse = response.block();
            log.debug("Respuesta desde la tienda (cliente) => " + storeResponse);

            // Partes NUEVAS:
            // Para éxito, también devolvemos un 200,
            // y el body con "OK|" para distinguirlo de un error
            return ResponseEntity.ok("OK|Pago procesado correctamente.");
        } catch (IllegalArgumentException ex) {
            // Podrías devolver un "ERROR|" o algo similar
            return ResponseEntity.ok("ERROR|Error 404");
        }
    }
}
