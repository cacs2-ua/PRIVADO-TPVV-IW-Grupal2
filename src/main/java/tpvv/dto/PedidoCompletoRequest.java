package tpvv.dto;

import java.util.Date;
import java.util.Objects;

public class PedidoCompletoRequest {

    private Long id;
    private Long pagoId;
    private Long pedidoId;
    private String ticketExt;
    private Date fecha;
    private double importe;
    private String tarjeta;
    private String estadoPago;
    private String comercioNombre;
    private String tarjetaPagoNumero;

    // Constructor vacío
    public PedidoCompletoRequest() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoCompletoRequest that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public String toString() {
        return "PedidoCompletoRequest{" +
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

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
