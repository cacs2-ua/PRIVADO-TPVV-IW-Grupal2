package tpvv.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpvv.dto.PagoCompletoRequest;
import tpvv.dto.PagoData;
import tpvv.dto.PedidoCompletoRequest;
import tpvv.dto.TarjetaPagoData;
import tpvv.model.Comercio;
import tpvv.model.EstadoPago;
import tpvv.model.Pago;
import tpvv.model.TarjetaPago;
import tpvv.repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TarjetaPagoRepository tarjetaPagoRepository;

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    /**
     * Obtiene la URL de retorno (back) del comercio basado en la API Key.
     *
     * @param apiKey Clave API del comercio.
     * @return URL de retorno.
     */
    @Transactional
    public String obtenerUrlBack(String apiKey) {
        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
        }
        return comercio.getUrl_back();
    }

    @Transactional
    public String obtenerNombreComercio(String apiKey) {
        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
        }
        return comercio.getNombre();
    }

    /**
     * Procesa el pago recibido y persiste la información en la base de datos.
     *
     * @param request Objeto con la información de pago y de la tarjeta.
     * @param apiKey  Clave API del comercio.
     * @return Objeto PedidoCompletoRequest que se enviará a la tienda.
     * @throws IllegalArgumentException En caso de datos incompletos o inválidos.
     */
    @Transactional
    public PedidoCompletoRequest procesarPago(PagoCompletoRequest request, String apiKey) {
        // Obtener PagoData
        PagoData pagoData = request.getPagoData();
        if (pagoData == null) {
            throw new IllegalArgumentException("Error: Falta el objeto PagoData en la petición.");
        }

        // Parsear el importe de String a double
        double importeDouble;
        try {
            importeDouble = Double.parseDouble(pagoData.getImporte());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Importe no válido.");
        }

        // Parsear la fecha de String a Date
        Date fechaDate;
        try {
            // Asumiendo que la fecha viene en formato "dd/MM/yyyy HH:mm"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            fechaDate = sdf.parse(pagoData.getFecha());
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException("Error: Formato de fecha no válido o fecha nula.");
        }

        if (importeDouble <= 0 ||
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

        // Transformar TarjetaPagoData (cvc, fechaCaducidad) a sus tipos nativos
        int cvcInt;
        try {
            cvcInt = Integer.parseInt(tarjetaData.getCvc());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: CVC no válido.");
        }

        // Parseo de la fecha de caducidad (String -> Date), asumiendo formato "MM/yy"
        Date fechaCaducDate;
        try {
            SimpleDateFormat sdfCaduc = new SimpleDateFormat("MM/yy");
            fechaCaducDate = sdfCaduc.parse(tarjetaData.getFechaCaducidad());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error: Formato de fechaCaducidad no válido (se esperaba MM/yy).");
        }

        // Crear entidad TarjetaPago
        TarjetaPago tarjetaPago = new TarjetaPago();
        tarjetaPago.setNumeroTarjeta(tarjetaData.getNumeroTarjeta().trim());
        tarjetaPago.setCvc(cvcInt);  // int
        tarjetaPago.setFechaCaducidad(fechaCaducDate);
        tarjetaPago.setNombre(tarjetaData.getNombre().trim());

        // Intentar reutilizar si ya existe en BD
        Optional<TarjetaPago> tarjetaExistenteOpt =
                tarjetaPagoRepository.findByNumeroTarjeta(tarjetaPago.getNumeroTarjeta());
        if (tarjetaExistenteOpt.isPresent()) {
            TarjetaPago tarjetaExistente = tarjetaExistenteOpt.get();
            // Actualizamos cvc, fechaCaducidad, nombre
            tarjetaExistente.setCvc(tarjetaPago.getCvc());
            tarjetaExistente.setFechaCaducidad(tarjetaPago.getFechaCaducidad());
            tarjetaExistente.setNombre(tarjetaPago.getNombre());
            tarjetaPago = tarjetaExistente;
        }
        // Guardar Tarjeta en la BD
        tarjetaPagoRepository.save(tarjetaPago);

        Comercio comercio = comercioRepository.findByApiKey(apiKey).orElse(null);
        if (comercio == null) {
            throw new IllegalArgumentException("Comercio no encontrado para la API Key proporcionada.");
        }

        // Crear o asignar estado de pago
        EstadoPago estadoPago = new EstadoPago("acept001", "Pago procesado correctamente.");
        estadoPagoRepository.save(estadoPago);

        // Construir entidad Pago a partir de PagoData
        Pago pago = new Pago();
        pago.setImporte(importeDouble);               // double
        pago.setTicketExt(pagoData.getTicketExt());
        pago.setFecha(fechaDate);                     // Date convertido desde String
        pago.setTarjetaPago(tarjetaPago);
        pago.setEstado(estadoPago);

        // Asignar el comercio
        pago.setComercio(comercio);

        // Guardar el Pago
        pagoRepository.save(pago);

        // Construir PedidoCompletoRequest para enviarlo al cliente
        PedidoCompletoRequest pedidoCompletoRequest = new PedidoCompletoRequest();
        pedidoCompletoRequest.setId(pago.getId());
        pedidoCompletoRequest.setPagoId(pago.getId());
        pedidoCompletoRequest.setPedidoId(4L); // ID de prueba
        pedidoCompletoRequest.setTicketExt(pago.getTicketExt());
        pedidoCompletoRequest.setFecha(pago.getFecha());
        pedidoCompletoRequest.setImporte(pago.getImporte());
        pedidoCompletoRequest.setEstadoPago(pago.getEstado().getNombre());
        pedidoCompletoRequest.setComercioNombre(pago.getComercio().getNombre());
        pedidoCompletoRequest.setNumeroTarjeta(pago.getTarjetaPago().getNumeroTarjeta());

        return pedidoCompletoRequest;
    }
}
