// model/Usuario.java

package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String nombre;

    @NotNull
    private String contrasenya;

    // Relación Many-to-One con Comercio
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id", nullable = false)
    private Comercio comercio;

    public Usuario() {}

    public Usuario(String email, String nombre, String contrasenya) {
        this.email = email;
        this.nombre = nombre;
        this.contrasenya = contrasenya;
    }

    // Getters y Setters básicos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    // Getter y Setter de la Relación Many-to-One

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        if (this.comercio != null) {
            this.comercio.getUsuarios().remove(this);
        }
        this.comercio = comercio;
        if (comercio != null && !comercio.getUsuarios().contains(this)) {
            comercio.getUsuarios().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;
        if (id != null && usuario.id != null)
            return Objects.equals(id, usuario.id);
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
