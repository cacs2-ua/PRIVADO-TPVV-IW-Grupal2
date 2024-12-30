package tpvv.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpvv.dto.PagoCompletoRequest;
import tpvv.dto.PagoData;
import tpvv.dto.TarjetaPagoData;
import tpvv.model.Comercio;
import tpvv.model.Pago;
import tpvv.model.TarjetaPago;
import tpvv.model.EstadoPago;
import tpvv.repository.PagoRepository;
import tpvv.repository.TarjetaPagoRepository;
import tpvv.repository.EstadoPagoRepository;

import java.util.Optional;

@RestController
@RequestMapping("/pago")
public class PagoRestController {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarjetaPagoRepository tarjetaPagoRepository;

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    /**
     * Procesa el pago realizado, recibiendo en un solo body:
     * - PagoData (importe, ticketExt, fecha, etc.)
     * - TarjetaPagoData (numeroTarjeta, cvc, fechaCaducidad, nombre)
     */
    @PostMapping("/realizar")
    public ResponseEntity<String> realizarPago(@Valid @RequestBody PagoCompletoRequest request) {

        // Obtenemos PagoData
        PagoData pagoData = request.getPagoData();
        if (pagoData == null) {
            return ResponseEntity.badRequest().body("Error: Falta el objeto PagoData en la petición.");
        }
        if (pagoData.getImporte() <= 0 ||
                pagoData.getTicketExt() == null ||
                pagoData.getFecha() == null) {
            return ResponseEntity.badRequest().body("Error: Datos de PagoData incompletos (importe/ticketExt/fecha).");
        }

        // Obtenemos TarjetaPagoData
        TarjetaPagoData tarjetaData = request.getTarjetaPagoData();
        if (tarjetaData == null) {
            return ResponseEntity.badRequest().body("Error: Falta el objeto TarjetaPagoData en la petición.");
        }
        if (tarjetaData.getNumeroTarjeta() == null ||
                tarjetaData.getFechaCaducidad() == null ||
                tarjetaData.getNombre() == null) {
            return ResponseEntity.badRequest().body("Error: Datos de TarjetaPagoData incompletos.");
        }

        // Transformar TarjetaPagoData a TarjetaPago (Entidad)
        TarjetaPago tarjetaPago = new TarjetaPago();
        tarjetaPago.setNumeroTarjeta(tarjetaData.getNumeroTarjeta());
        tarjetaPago.setCvc(tarjetaData.getCvc());
        tarjetaPago.setFechaCaducidad(tarjetaData.getFechaCaducidad());
        tarjetaPago.setNombre(tarjetaData.getNombre());

        // Intentar reutilizar si ya existe en BD
        Optional<TarjetaPago> tarjetaExistenteOpt =
                tarjetaPagoRepository.findByNumeroTarjeta(tarjetaPago.getNumeroTarjeta());
        if (tarjetaExistenteOpt.isPresent()) {
            TarjetaPago tarjetaExistente = tarjetaExistenteOpt.get();
            // actualizamos cvc, fechaCaducidad, nombre si es necesario
            tarjetaExistente.setCvc(tarjetaPago.getCvc());
            tarjetaExistente.setFechaCaducidad(tarjetaPago.getFechaCaducidad());
            tarjetaExistente.setNombre(tarjetaPago.getNombre());
            tarjetaPago = tarjetaExistente;
        }

        // Guardar Tarjeta en la BD
        tarjetaPagoRepository.save(tarjetaPago);

        EstadoPago estadoPago = new EstadoPago("acept001", "Pago procesado correctamente.");

        estadoPagoRepository.save(estadoPago);

        // Construir entidad Pago a partir de PagoData
        Pago pago = new Pago();
        pago.setImporte(pagoData.getImporte());
        pago.setTicketExt(pagoData.getTicketExt());
        pago.setFecha(pagoData.getFecha());

        // Asignar un comercio cualquiera. En tu caso, quizá lo obtienes
        // de la DB a partir de su CIF o ID. Aquí, por ejemplo:
        pago.setComercio(new Comercio("B12345678"));

        // Asignar la Tarjeta
        pago.setTarjetaPago(tarjetaPago);

        // Guardar el Pago
        pagoRepository.save(pago);

        // Retornar respuesta
        return ResponseEntity.ok("Pago procesado correctamente. ID del pago: " + pago.getId());
    }
}
