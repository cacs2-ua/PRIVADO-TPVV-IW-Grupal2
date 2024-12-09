package madstodolist.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private  String ticketExt;

    @NotNull
    private Date fecha;

    @NotNull
    private double importe;

    @NotNull
    private String tarjeta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "comercio_id", nullable = false)
    private Comercio comercio;

    @OneToOne(mappedBy = "pago", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Incidencia incidencia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoPago estado;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tarjeta_pago_id", nullable = false)
    private TarjetaPago tarjetaPago;

    public Pago() {}

    public Pago(String ticketExt) {
        this.ticketExt = ticketExt;
        this.fecha = new Date();
        this.importe = 0.0;
        this.tarjeta = "default";
        this.comercio = new Comercio("default");
        this.estado = new EstadoPago("default");
    }

    public Pago(String ticketExt, Date fecha, double importe, String tarjeta, Comercio comercio) {
        this.ticketExt = ticketExt;
        this.fecha = fecha;
        this.importe = importe;
        this.tarjeta = tarjeta;
        this.comercio = comercio;
        this.estado = new EstadoPago("default");
    }

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

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        // Si el nuevo comercio es el mismo que el actual, no hace nada
        if (this.comercio == comercio) {
            return;
        }

        // Si ya tiene un comercio, lo desvincula de la lista de pagos de ese comercio
        if (this.comercio != null) {
            this.comercio.getPagos().remove(this);
        }

        // Asigna el nuevo comercio
        this.comercio = comercio;

        // Si el comercio no es nulo, lo añade a la lista de pagos de ese comercio
        if (comercio != null && !comercio.getPagos().contains(this)) {
            comercio.addPago(this);
        }
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        if (this.incidencia != null) {
            this.incidencia.setPago(null);
        }
        this.incidencia = incidencia;
        if (incidencia != null && incidencia.getPago() != this) {
            incidencia.setPago(this);
        }
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        // Si el nuevo estado es el mismo que el actual, no hace nada
        if (this.estado == estado) {
            return;
        }

        // Si ya tiene un estado, lo desvincula de la lista de pagos de ese estado
        if (this.estado != null) {
            this.estado.getPagos().remove(this);
        }

        // Asigna el nuevo estado
        this.estado = estado;

        // Si el estado no es nulo, lo añade a la lista de pagos de ese estado
        if (estado != null && !estado.getPagos().contains(this)) {
            estado.addPago(this);
        }
    }

    public TarjetaPago getTarjetaPago() {
        return tarjetaPago;
    }

    public void setTarjetaPago(TarjetaPago tarjetaPago) {
        // Si la nueva tarjetaPago es la misma que la actual, no hace nada
        if (this.tarjetaPago == tarjetaPago) {
            return;
        }

        // Si ya tiene una tarjetaPago, lo desvincula de la lista de pagos de esa tarjetaPago
        if (this.tarjetaPago != null) {
            this.tarjetaPago.getPagos().remove(this);
        }

        // Asigna la nueva tarjetaPago
        this.tarjetaPago = tarjetaPago;

        // Si la tarjetaPago no es nulo, lo añade a la lista de pagos de esa tarjetaPago
        if (tarjetaPago != null && !tarjetaPago.getPagos().contains(this)) {
            tarjetaPago.addPago(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pago that = (Pago) o;

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
