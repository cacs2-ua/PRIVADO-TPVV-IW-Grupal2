package tpvv.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpvv.dto.PagoCompletoRequest;
import tpvv.dto.PedidoCompletoRequest;
import tpvv.service.PagoService;

// NUEVO: Importaciones adicionales para WebClient
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/pago")
public class PagoRestController {

    private static final Logger log = LoggerFactory.getLogger(PagoRestController.class);

    @Autowired
    private PagoService pagoService;

    // NUEVO: Inyectamos el WebClient (definido en WebClientConfig)
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

        // Si la cabecera viene nula o en blanco, manejarlo según tu lógica
        String apiKey = (authorizationHeader != null) ? authorizationHeader.trim() : "";

        try {
            String urlBack = pagoService.obtenerUrlBack(apiKey);
            PedidoCompletoRequest pedidoCompletoRequest = pagoService.procesarPago(request, apiKey);

            // NUEVO: Llamada POST al proyecto cliente, enviando pedidoCompletoRequest
            // ------------------------------------------------------------------------------
            Mono<String> response = webClient.post()
                    .uri(urlBack)  // El endpoint en la Tienda
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(pedidoCompletoRequest))
                    .retrieve()
                    .bodyToMono(String.class);

            // Bloqueamos para obtener la respuesta (podrías manejarlo con programación reactiva si lo deseas)
            String storeResponse = response.block();
            log.debug("Respuesta desde la tienda (cliente) => " + storeResponse);
            // ------------------------------------------------------------------------------

            return ResponseEntity.ok("Pago procesado correctamente.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error 404");
        }
    }
}
