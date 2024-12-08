package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "estados_pago")
public class EstadoPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    private String razonEstado;

    public EstadoPago() {}

    public EstadoPago(String nombre) {
        this.nombre = nombre;
    }

    public EstadoPago(String nombre, String razonEstado) {
        this.nombre = nombre;
        this.razonEstado = razonEstado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonEstado() {
        return razonEstado;
    }

    public void setRazonEstado(String razonEstado) {
        this.razonEstado = razonEstado;
    }

}
