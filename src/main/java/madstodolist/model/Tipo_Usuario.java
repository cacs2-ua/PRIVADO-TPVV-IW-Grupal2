package madstodolist.model;

import org.w3c.dom.Text;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tipos_usuario")
public class Tipo_Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Usuario> usuarios = new HashSet<>();

    public Tipo_Usuario() {}

    public Tipo_Usuario(String nombre) {
        this.nombre = nombre;

    }

    public Tipo_Usuario(String nombre, Usuario usuario) {
        this.nombre = nombre;

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

    public Set<Usuario> getUsuarios() {
        return  usuarios;
    }

    public void addUsuario(Usuario usuario) {
        if (usuarios.contains(usuario)) return;
        usuarios.add(usuario);
        if (usuario.getTipo() != this) {
            usuario.setTipo(this);
        }
    }


}

