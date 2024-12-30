package tpvv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpvv.dto.PagoCompletoRequest;
import tpvv.dto.PagoData;
import tpvv.dto.TarjetaPagoData;
import tpvv.model.Comercio;
import tpvv.model.EstadoPago;
import tpvv.model.Pago;
import tpvv.model.TarjetaPago;
import tpvv.repository.EstadoPagoRepository;
import tpvv.repository.PagoRepository;
import tpvv.repository.TarjetaPagoRepository;

import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarjetaPagoRepository tarjetaPagoRepository;

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    /**
     * Procesa el pago recibido y persiste la información en la base de datos.
     *
     * @param request  Objeto con la información de pago y de la tarjeta.
     * @param apiKey   Clave API (si la necesitas).
     * @return         Mensaje de éxito con el ID del pago.
     * @throws IllegalArgumentException En caso de datos incompletos o inválidos.
     */
    public String procesarPago(PagoCompletoRequest request, String apiKey) {
        // Obtener PagoData
        PagoData pagoData = request.getPagoData();
        if (pagoData == null) {
            throw new IllegalArgumentException("Error: Falta el objeto PagoData en la petición.");
        }
        if (pagoData.getImporte() <= 0 ||
                pagoData.getTicketExt() == null ||
                pagoData.getFecha() == null) {
            throw new IllegalArgumentException("Error: Datos de PagoData incompletos (importe/ticketExt/fecha).");
        }

        // Obtener TarjetaPagoData
        TarjetaPagoData tarjetaData = request.getTarjetaPagoData();
        if (tarjetaData == null) {
            throw new IllegalArgumentException("Error: Falta el objeto TarjetaPagoData en la petición.");
        }
        if (tarjetaData.getNumeroTarjeta() == null ||
                tarjetaData.getFechaCaducidad() == null ||
                tarjetaData.getNombre() == null) {
            throw new IllegalArgumentException("Error: Datos de TarjetaPagoData incompletos.");
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
            // Actualizamos cvc, fechaCaducidad, nombre si es necesario
            tarjetaExistente.setCvc(tarjetaPago.getCvc());
            tarjetaExistente.setFechaCaducidad(tarjetaPago.getFechaCaducidad());
            tarjetaExistente.setNombre(tarjetaPago.getNombre());
            tarjetaPago = tarjetaExistente;
        }

        // Guardar Tarjeta en la BD
        tarjetaPagoRepository.save(tarjetaPago);

        // Crear o asignar estado de pago
        EstadoPago estadoPago = new EstadoPago("acept001", "Pago procesado correctamente.");
        estadoPagoRepository.save(estadoPago);

        // Construir entidad Pago a partir de PagoData
        Pago pago = new Pago();
        pago.setImporte(pagoData.getImporte());
        pago.setTicketExt(pagoData.getTicketExt());
        pago.setFecha(pagoData.getFecha());

        // Asignar un comercio (ejemplo)
        pago.setComercio(new Comercio("B12345678"));

        // Asignar la Tarjeta
        pago.setTarjetaPago(tarjetaPago);

        // Guardar el Pago
        pagoRepository.save(pago);

        // Retornar respuesta
        return "Pago procesado correctamente. ID del pago: " + pago.getId();
    }
}
