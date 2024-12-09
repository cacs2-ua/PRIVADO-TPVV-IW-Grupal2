package madstodolist.dto;

import java.util.Date;

public class PagoData {
    private Long id;
    private String ticketExt;
    private Date fecha;
    private double importe;
    private String tarjeta;
    private String estadoPago;
    private String comercioNombre;
    private String tarjetaPagoNumero;

    // Constructor vacío
    public PagoData() {}

    // Constructor completo
    public PagoData(Long id, String ticketExt, Date fecha, double importe, String tarjeta, String estadoPago, String comercioNombre, String tarjetaPagoNumero) {
        this.id = id;
        this.ticketExt = ticketExt;
        this.fecha = fecha;
        this.importe = importe;
        this.tarjeta = tarjeta;
        this.estadoPago = estadoPago;
        this.comercioNombre = comercioNombre;
        this.tarjetaPagoNumero = tarjetaPagoNumero;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketExt() {
        return ticketExt;
    }

    public void setTicketExt(String ticketExt) {
        this.ticketExt = ticketExt;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getComercioNombre() {
        return comercioNombre;
    }

    public void setComercioNombre(String comercioNombre) {
        this.comercioNombre = comercioNombre;
    }

    public String getTarjetaPagoNumero() {
        return tarjetaPagoNumero;
    }

    public void setTarjetaPagoNumero(String tarjetaPagoNumero) {
        this.tarjetaPagoNumero = tarjetaPagoNumero;
    }

    @Override
    public String toString() {
        return "PagoData{" +
                "id=" + id +
                ", ticketExt='" + ticketExt + '\'' +
                ", fecha=" + fecha +
                ", importe=" + importe +
                ", tarjeta='" + tarjeta + '\'' +
                ", estadoPago='" + estadoPago + '\'' +
                ", comercioNombre='" + comercioNombre + '\'' +
                ", tarjetaPagoNumero='" + tarjetaPagoNumero + '\'' +
                '}';
    }
}
