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

    public Pago() {}

    public Pago(String ticketExt) {
        this.ticketExt = ticketExt;
        this.fecha = new Date();
        this.importe = 0.0;
        this.tarjeta = "default";
        this.comercio = new Comercio("default");
    }

    public Pago(String ticketExt, Date fecha, double importe, String tarjeta, Comercio comercio) {
        this.ticketExt = ticketExt;
        this.fecha = fecha;
        this.importe = importe;
        this.tarjeta = tarjeta;
        this.comercio = comercio;
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

}
