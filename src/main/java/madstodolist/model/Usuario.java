// model/Usuario.java

package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "comercio_id", nullable = false)
    private Comercio comercio;

    @OneToMany(mappedBy = "usuario_comercio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Incidencia> incidencias_comercio = new HashSet<>();

    public Usuario() {}

    public Usuario(String email, String nombre, String contrasenya) {
        this.email = email;
        this.nombre = nombre;
        this.contrasenya = contrasenya;
    }

    public Usuario(String email, String nombre, String contrasenya, Comercio comercio) {
        this.email = email;
        this.nombre = nombre;
        this.contrasenya = contrasenya;
        this.setComercio(comercio);
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

    public Set<Incidencia> getIncidencias_comercio() {
        return incidencias_comercio;
    }

    public void addIncidencia_comercio(Incidencia incidencia) {
        if (incidencias_comercio.contains(incidencia)) return;
        incidencias_comercio.add(incidencia);
        if (incidencia.getUsuario_comercio() != this) {
            incidencia.setUsuario_comercio(this);
        }
    }

    public void removeIncidencia_comercio(Incidencia incidencia) {
        if (!incidencias_comercio.contains(incidencia)) return;
        incidencias_comercio.remove(incidencia);
        if (incidencia.getUsuario_comercio() == this) {
            incidencia.setUsuario_comercio(null);
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
