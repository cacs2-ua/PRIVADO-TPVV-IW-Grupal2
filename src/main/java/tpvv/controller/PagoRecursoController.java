package tpvv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.text.SimpleDateFormat; // <-- NUEVO
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

    // =========================== LISTAR PAGOS DEL COMERCIO ===========================
    @GetMapping("/api/comercio/{id}/pagos")
    public String listarPagosComercio(@PathVariable(value="id") Long idUsuario,
                                      Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String ticket,
                                      @RequestParam(required = false) String estado,

                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,

                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {

        Long idUsuarioLogeado = devolverIdUsuarioLogeado();
        if (!idUsuario.equals(idUsuarioLogeado)) {
            throw new UsuarioNoLogeadoException();
        }

        ComercioData comercioData = pagoService.obtenerComercioDeUsuarioLogeado(idUsuarioLogeado);

        Timestamp tsDesde = null;
        if (fechaDesde != null) {
            tsDesde = new Timestamp(fechaDesde.getTime());
        }

        Timestamp tsHasta = null;
        if (fechaHasta != null) {
            tsHasta = new Timestamp(fechaHasta.getTime());
        }

        List<PagoRecursoData> pagos = pagoService.filtrarPagosDeUnComercio(
                comercioData.getId(),
                id,
                ticket,
                estado,
                tsDesde,
                tsHasta
        );

        // === NUEVO: Convertir fechaDesde y fechaHasta a string 'yyyy-MM-dd' para ponerlo en el input date ===
        String fechaDesdeStr = (fechaDesde != null) ? new SimpleDateFormat("yyyy-MM-dd").format(fechaDesde) : "";
        String fechaHastaStr = (fechaHasta != null) ? new SimpleDateFormat("yyyy-MM-dd").format(fechaHasta) : "";

        // Meter en el modelo para repintar el input date
        model.addAttribute("fechaDesdeStr", fechaDesdeStr);
        model.addAttribute("fechaHastaStr", fechaHastaStr);

        // Otros filtros
        model.addAttribute("idFilter", id);
        model.addAttribute("ticketFilter", ticket);
        model.addAttribute("estadoFilter", estado);

        model.addAttribute("pagos", pagos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 1); // Si no paginas aquí, pon un 1 fijo, o lo que requieras.

        return "listadoPagosComercio";
    }

    // =========================== LISTAR PAGOS (ADMIN) ===========================
    @GetMapping("/api/admin/pagos")
    public String allPagos(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(required = false) Long id,
                           @RequestParam(required = false) String ticket,
                           @RequestParam(required = false) String cif,
                           @RequestParam(required = false) String estado,

                           @RequestParam(required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,

                           @RequestParam(required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {

        Timestamp tsDesde = null;
        if (fechaDesde != null) {
            tsDesde = new Timestamp(fechaDesde.getTime());
        }

        Timestamp tsHasta = null;
        if (fechaHasta != null) {
            tsHasta = new Timestamp(fechaHasta.getTime());
        }

        // Obtenemos la página con paginación
        Page<PagoRecursoData> pageResult = pagoService.filtrarPagosPaginado(
                page,
                4,
                id,
                ticket,
                cif,
                estado,
                tsDesde,
                tsHasta
        );

        List<PagoRecursoData> pagos = pageResult.getContent();

        // Convertimos para repintar el input date
        String fechaDesdeStr = (fechaDesde != null) ? new SimpleDateFormat("yyyy-MM-dd").format(fechaDesde) : "";
        String fechaHastaStr = (fechaHasta != null) ? new SimpleDateFormat("yyyy-MM-dd").format(fechaHasta) : "";

        // Guardamos en el modelo
        model.addAttribute("pagos", pagos);

        // Paginación
        model.addAttribute("currentPage", pageResult.getNumber());
        model.addAttribute("totalPages", pageResult.getTotalPages());

        // Filtros
        model.addAttribute("idFilter", id);
        model.addAttribute("ticketFilter", ticket);
        model.addAttribute("cifFilter", cif);
        model.addAttribute("estadoFilter", estado);

        model.addAttribute("fechaDesdeStr", fechaDesdeStr); // <-- string formateado
        model.addAttribute("fechaHastaStr", fechaHastaStr); // <-- string formateado

        return "listadoPagos";
    }

    // =========================== DETALLES PAGO ===========================
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

    private String formatCardNumberWithMask(String numeroTarjeta) {
        if (numeroTarjeta.length() != 16) {
            throw new IllegalArgumentException("El número de tarjeta debe tener exactamente 16 dígitos.");
        }

        String maskedPart = "**** **** ****";
        String visiblePart = numeroTarjeta.substring(12);
        return maskedPart + " " + visiblePart;
    }
}
