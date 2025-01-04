package tpvv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tpvv.authentication.ManagerUserSession;
import tpvv.controller.exception.UsuarioNoLogeadoException;
import tpvv.dto.ComercioData;
import tpvv.dto.PagoRecursoData;
import tpvv.service.PagoService;

import java.sql.Timestamp;
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
                                      Model model) {
        Long idUsuarioLogeado = devolverIdUsuarioLogeado();

        if (!idUsuario.equals(idUsuarioLogeado))
            throw new UsuarioNoLogeadoException();

        ComercioData comercioData = pagoService.obtenerComercioDeUsuarioLogeado(idUsuarioLogeado);

        List<PagoRecursoData> pagos = pagoService.obtenerPagosDeUnComercio(comercioData.getId());

        for (PagoRecursoData pago : pagos) {
            if (pago.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                pago.setShownState("Aceptado");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("PEND")) {
                pago.setShownState("Pendiente");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("RECH")) {
                pago.setShownState("Rechazado");
            }

        }

        model.addAttribute("pagos", pagos);

        return "listadoPagosComercio";
    }

    @GetMapping("/api/admin/pagos")
    public String allPagos(Model model) {

        List<PagoRecursoData> pagos = pagoService.allPagos();

        for (PagoRecursoData pago : pagos) {
            if (pago.getEstadoPagoData().getNombre().startsWith("ACEPT")) {
                pago.setShownState("Aceptado");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("PEND")) {
                pago.setShownState("Pendiente");
            }

            else if (pago.getEstadoPagoData().getNombre().startsWith("RECH")) {
                pago.setShownState("Rechazado");
            }

        }

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
                                    Model model,
                                    @RequestParam(required = false) Long id,
                                    @RequestParam(required = false) String ticket,
                                    @RequestParam(required = false) String cif,
                                    @RequestParam(required = false) String estado,
                                    @RequestParam(required = false) Timestamp fechaDesde,
                                    @RequestParam(required = false) Timestamp fechaHasta) {
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
