package madstodolist.model;

import org.w3c.dom.Text;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;

    public Mensaje() {}

    public Mensaje(String contenido) {
        this.contenido = contenido;
        Usuario usuario = new Usuario("email");
        this.setUsuario(usuario);
        this.fecha = new Date("2000-12-12");
        this.incidencia = new Incidencia("default");
    }

    public Mensaje(String contenido, Usuario usuario) {
        this.contenido = contenido;
        this.setUsuario(usuario);
        this.fecha = new Date("2000-12-12");
        this.incidencia = new Incidencia("default");
    }

    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        // Si el nuevo usuario es el mismo que el actual, no hace nada
        if (this.usuario == usuario) {
            return;
        }

        // Si ya tiene un usuario, lo desvincula de la lista de mensajes de ese usuario
        if (this.usuario != null) {
            this.usuario.getMensajes().remove(this);
        }

        // Asigna el nuevo usuario
        this.usuario = usuario;

        // Si el usuario no es nulo, lo añade a la lista de mensajes de ese usuario
        if (usuario != null && !usuario.getMensajes().contains(this)) {
            usuario.getMensajes().add(this);
        }
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }


    public void setIncidencia(Incidencia incidencia) {
        // Si la nueva incidencia es la misma que la actual, no hace nada
        if (this.incidencia == incidencia) {
            return;
        }

        // Si ya tiene una incidencia, lo desvincula de la lista de mensajes de esa incidencia
        if (this.incidencia != null) {
            this.incidencia.getMensajes().remove(this);
        }

        // Asigna la nueva incidencia
        this.incidencia = incidencia;

        // Si la incidencia no es nula, lo añade a la lista de mensajes de esa incidencia
        if (incidencia != null && !incidencia.getMensajes().contains(this)) {
            incidencia.addMensaje(this);
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
