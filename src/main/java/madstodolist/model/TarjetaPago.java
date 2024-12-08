package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tarjetas_pago")
public class TarjetaPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String numeroTarjeta;

    @NotNull
    private int cvc;

    @NotNull
    private Date fechaCaducidad;

    @NotNull
    private String nombre;

    public TarjetaPago() {}

    public TarjetaPago(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.cvc = 0;
        this.fechaCaducidad = new Date();
        this.nombre = "default";
    }

    public TarjetaPago(String numeroTarjeta, int cvc, Date fechaCaducidad, String nombre) {
        this.numeroTarjeta = numeroTarjeta;
        this.cvc = cvc;
        this.fechaCaducidad = fechaCaducidad;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TarjetaPago tarjetaPago = (TarjetaPago) o;
        if (id != null && tarjetaPago.id != null)
            return Objects.equals(id, tarjetaPago.id);
        return Objects.equals(numeroTarjeta, tarjetaPago.numeroTarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroTarjeta);
    }
}
