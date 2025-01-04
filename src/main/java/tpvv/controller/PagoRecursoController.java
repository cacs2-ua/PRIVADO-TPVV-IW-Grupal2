package tpvv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tpvv.authentication.ManagerUserSession;
import tpvv.controller.exception.UsuarioNoLogeadoException;
import tpvv.dto.ComercioData;
import tpvv.dto.PagoRecursoData;
import tpvv.service.PagoService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class PagoRecursoController {

    @Autowired
    ManagerUserSession managerUserSession;

    private Long devolverIdUsuarioLogeado() {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado == null)
            throw new UsuarioNoLogeadoException();
        return idUsuarioLogeado;
    }

    @Autowired
    PagoService pagoService;

    @GetMapping("/api/comercio/{id}/pagos")
    public String listarPagosComercio(@PathVariable(value="id") Long idUsuario,
                                      Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String ticket,
                                      @RequestParam(required = false) String estado,

                                      // Fechas
                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,

                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {

        Long idUsuarioLogeado = devolverIdUsuarioLogeado();
        if (!idUsuario.equals(idUsuarioLogeado)) {
            throw new UsuarioNoLogeadoException();
        }

        // 1) Obtener el comercio
        ComercioData comercioData = pagoService.obtenerComercioDeUsuarioLogeado(idUsuarioLogeado);

        // 2) Convertir las fechas a Timestamp (si hace falta)
        Timestamp tsDesde = null;
        if (fechaDesde != null) {
            tsDesde = new Timestamp(fechaDesde.getTime());
        }

        Timestamp tsHasta = null;
        if (fechaHasta != null) {
            tsHasta = new Timestamp(fechaHasta.getTime());
        }

        // 3) Invocar el NUEVO método filtrarPagosDeUnComercio(...) en PagoService
        List<PagoRecursoData> pagos = pagoService.filtrarPagosDeUnComercio(
                comercioData.getId(), // ID del comercio
                id,
                ticket,
                estado,
                tsDesde,
                tsHasta
        );

        // 4) Pasar los datos a la vista
        model.addAttribute("pagos", pagos);

        return "listadoPagosComercio";
    }

    @GetMapping("/api/admin/pagos")
    public String allPagos(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(required = false) Long id,
                           @RequestParam(required = false) String ticket,
                           @RequestParam(required = false) String cif,
                           @RequestParam(required = false) String estado,

                           // IMPORTANTE: Con @DateTimeFormat, si viene "" => null,
                           // y si viene "2025-01-04" => se parsea a Date con la hora a 00:00:00.
                           @RequestParam(required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,

                           @RequestParam(required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {

        // 1) Convertir las fechas a Timestamp (si tu servicio necesita Timestamps).
        //    Si no, pásale Date directamente.
        Timestamp tsDesde = null;
        if (fechaDesde != null) {
            tsDesde = new Timestamp(fechaDesde.getTime());
        }

        Timestamp tsHasta = null;
        if (fechaHasta != null) {
            // Opcional: si quieres incluir todo el día "fechaHasta"
            // podrías sumarle 23h59m59s. Aquí lo dejamos tal cual.
            tsHasta = new Timestamp(fechaHasta.getTime());
        }

        // 2) Invocar la lógica de filtrado en PagoService
        List<PagoRecursoData> pagos = pagoService.filtrarPagos(id, ticket, cif, estado, tsDesde, tsHasta);

        // 3) Pasar los datos a la vista
        model.addAttribute("pagos", pagos);
        return "listadoPagos";
    }


    private String formatCardNumberWithMask(String numeroTarjeta) {
        if (numeroTarjeta.length() != 16) {
            throw new IllegalArgumentException("El número de tarjeta debe tener exactamente 16 dígitos.");
        }

        // Máscara de los primeros 12 dígitos con '*'
        String maskedPart = "**** **** ****";
        // Últimos 4 dígitos del número de tarjeta
        String visiblePart = numeroTarjeta.substring(12);

        // Combinar ambos con un espacio entre los bloques
        return maskedPart + " " + visiblePart;
    }


    @GetMapping("/api/comercio/pagos/{id}")
    public String detallesPago(@PathVariable(value = "id") Long idPago,
                               Model model) {
        PagoRecursoData pago = pagoService.obtenerPagoPorId(idPago);

        if (pago.getTarjetaPagoData() != null && pago.getTarjetaPagoData().getNumeroTarjeta() != null) {
            String numeroTarjeta = pago.getTarjetaPagoData().getNumeroTarjeta();
            String numeroTarjetaFormateado = formatCardNumberWithMask(numeroTarjeta);
            pago.setFormatedCardNumber(numeroTarjetaFormateado);
        }

        model.addAttribute("pago", pago);

        return "detallesPago";
    }

    @GetMapping("/api/admin/pagos/{id}")
    public String detallesPagoAdmin(@PathVariable(value="id") Long idPago,
                                    Model model) {
        PagoRecursoData pago = pagoService.obtenerPagoPorId(idPago);

        if (pago.getTarjetaPagoData() != null && pago.getTarjetaPagoData().getNumeroTarjeta() != null) {
            String numeroTarjeta = pago.getTarjetaPagoData().getNumeroTarjeta();
            String numeroTarjetaFormateado = formatCardNumberWithMask(numeroTarjeta);
            pago.setFormatedCardNumber(numeroTarjetaFormateado);
        }

        model.addAttribute("pago", pago);

        return "detallesPagoAdmin";
    }
}
