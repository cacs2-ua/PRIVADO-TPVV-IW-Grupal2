package madstodolist.model;

import org.w3c.dom.Text;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "incidencias")
public class Incidencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date fecha;

    @NotNull
    private String titulo;

    @NotNull
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_comercio_id", nullable = false)
    private Usuario usuario_comercio;

    private int valoracion;

    private String razon_valoracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getRazon_valoracion() {
        return razon_valoracion;
    }

    public void setRazon_valoracion(String razon_valoracion) {
        this.razon_valoracion = razon_valoracion;
    }

    public Usuario getUsuario_comercio() {
        return usuario_comercio;
    }

    public void setUsuario_comercio(Usuario usuario_comercio) {
        if (this.usuario_comercio != null) {
            this.usuario_comercio.getIncidencias_comercio().remove(this);
        }
        this.usuario_comercio = usuario_comercio;
        if (usuario_comercio != null && !usuario_comercio.getIncidencias_comercio().contains(this)) {
            usuario_comercio.getIncidencias_comercio().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Incidencia that = (Incidencia) o;

        // Si ambos objetos tienen un ID no nulo, comparamos por ID
        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        // Si no se pueden comparar por ID, consideramos que son diferentes
        return false;
    }

    @Override
    public int hashCode() {
        // Generamos el hashCode basado únicamente en el ID
        return Objects.hash(id);
    }

}
