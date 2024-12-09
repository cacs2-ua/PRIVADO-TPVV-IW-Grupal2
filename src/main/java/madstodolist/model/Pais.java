package madstodolist.model;

import org.w3c.dom.Text;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "paises")
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "pais_id")
    private Set<Comercio> comercios = new HashSet<>();

    public  Pais() {}

    public Pais(String nombre) {
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

    public Set<Comercio> getComercios() {
        return comercios;
    }

    // add Comercio
    public void addComercio(Comercio comercio) {
        if (comercios.contains(comercio)) return;
        comercios.add(comercio);
        if (comercio.getPais_id() != this) {
            comercio.setPais_id(this);
        }
    }



}
