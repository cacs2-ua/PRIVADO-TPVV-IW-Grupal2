package tpvv.dto;

import tpvv.model.Pago;

import java.util.Date;
import java.util.Objects;

public class PagoRecursoData {

    private Long id;
    private String ticketExt;

    private Date fecha;

    private double importe;

    private String estado;

    private String tarjetaPago;

    // Constructor vacío
    public PagoRecursoData() {}

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTarjetaPago() {
        return tarjetaPago;
    }

    public void setTarjetaPago(String tarjetaPago) {
        this.tarjetaPago = tarjetaPago;
    }

    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagoRecursoData that = (PagoRecursoData) o;

        // Si ambos objetos tienen un ID no nulo, comparamos por ID
        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        // Si no se pueden comparar por ID, consideramos que son diferentes
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
