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

// NUEVO: Importaciones adicionales para WebClient y otras utilidades
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

        // ========================
        //  Validaciones
        // ========================
        PagoData pagoData = request.getPagoData();
        TarjetaPagoData tarjetaData = request.getTarjetaPagoData();

        // Construimos un StringBuilder para acumular mensajes de error (si los hubiera).
        StringBuilder errorMsg = new StringBuilder();

        // Validar que ninguno sea nulo
        if (pagoData == null) {
            errorMsg.append("PagoData no puede ser nulo. ");
        } else {
            if (pagoData.getImporte() == null || pagoData.getImporte().isBlank()) {
                errorMsg.append("El importe no puede ser nulo o vacío. ");
            }
            if (pagoData.getTicketExt() == null || pagoData.getTicketExt().isBlank()) {
                errorMsg.append("El ticketExt no puede ser nulo o vacío. ");
            }
            if (pagoData.getFecha() == null || pagoData.getFecha().isBlank()) {
                errorMsg.append("La fecha no puede ser nula o vacía. ");
            }
        }

        if (tarjetaData == null) {
            errorMsg.append("TarjetaPagoData no puede ser nulo. ");
        } else {
            if (tarjetaData.getNumeroTarjeta() == null || tarjetaData.getNumeroTarjeta().isBlank()) {
                errorMsg.append("El número de tarjeta no puede ser nulo o vacío. ");
            } else {
                // Validar que sea un número entero no negativo de EXACTAMENTE 16 dígitos
                if (!tarjetaData.getNumeroTarjeta().matches("^\\d{16}$")) {
                    errorMsg.append("El número de tarjeta debe tener exactamente 16 dígitos. ");
                }
            }

            // Validar fechaCaducidad con formato EXACTO mm/yy
            if (tarjetaData.getFechaCaducidad() == null || tarjetaData.getFechaCaducidad().isBlank()) {
                errorMsg.append("La fecha de caducidad no puede ser nula o vacía. ");
            } else {
                if (!tarjetaData.getFechaCaducidad().matches("^(0[1-9]|1[0-2])\\/\\d{2}$")) {
                    errorMsg.append("La fecha de caducidad debe tener el formato mm/yy. ");
                }
            }

            // Validar CVC
            if (tarjetaData.getCvc() == null || tarjetaData.getCvc().isBlank()) {
                errorMsg.append("El código de seguridad no puede ser nulo o vacío. ");
            } else {
                if (!tarjetaData.getCvc().matches("^\\d{3}$")) {
                    errorMsg.append("El CVC debe tener exactamente 3 dígitos. ");
                }
            }
        }

        // ==============================
        //  MODIFICADO:
        //  En lugar de devolver 302, devolvemos 400 con el texto de error
        // ==============================
        if (errorMsg.length() > 0) {
            // NUEVO: Devolvemos un 400 con el body = los mensajes de error
            return ResponseEntity.badRequest().body(errorMsg.toString());
        }
        // ==============================
        // FIN Validaciones
        // ==============================

        try {
            // Si no hubo errores, procesamos el pago normalmente:
            String urlBack = pagoService.obtenerUrlBack(apiKey);
            PedidoCompletoRequest pedidoCompletoRequest = pagoService.procesarPago(request, apiKey);

            // Llamada POST al proyecto cliente, enviando pedidoCompletoRequest
            Mono<String> response = webClient.post()
                    .uri(urlBack)  // El endpoint en la Tienda
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(pedidoCompletoRequest))
                    .retrieve()
                    .bodyToMono(String.class);

            // Bloqueamos para obtener la respuesta
            String storeResponse = response.block();
            log.debug("Respuesta desde la tienda (cliente) => " + storeResponse);

            return ResponseEntity.ok("Pago procesado correctamente.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Error 404");
        }
    }
}
