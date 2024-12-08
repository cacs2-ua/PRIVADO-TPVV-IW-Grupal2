package madstodolist.model;

import org.w3c.dom.Text;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "mensajes")
public class Mensaje implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date fecha;

    @NotNull
    String contenido;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Mensaje() {}

    public  Mensaje(String contenido) {
        this.contenido = contenido;
        Usuario usuario = new Usuario("email");
        this.setUsuario(usuario);
        this.fecha = new Date("2000-12-12");
    }

    public Mensaje(String contenido, Usuario usuario) {
        this.contenido = contenido;
        this.setUsuario(usuario);
        this.fecha = new Date("2000-12-12");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null) {
            this.usuario.getMensajes().remove(this);
        }
        this.usuario = usuario;
        if (usuario != null && !usuario.getMensajes().contains(this)) {
            usuario.getMensajes().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mensaje that = (Mensaje) o;

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
