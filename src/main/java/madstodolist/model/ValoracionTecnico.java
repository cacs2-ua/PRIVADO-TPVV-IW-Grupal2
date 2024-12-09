package madstodolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "incidencias")
public class ValoracionTecnico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private float valoracion;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    public ValoracionTecnico() {}

    public ValoracionTecnico(float valoracion) {
        this.valoracion = valoracion;
        this.tecnico = new Usuario("default");
    }

    public  ValoracionTecnico(float valoracion, Usuario tecnico) {
        this.valoracion = valoracion;
        this.tecnico = tecnico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        if (this.tecnico == tecnico) {
            return; // No hacer nada si es el mismo técnico
        }

        // Desvincular el técnico anterior si existe
        if (this.tecnico != null) {
            this.tecnico.setValoracionTecnico(null);
        }

        // Asignar el nuevo técnico
        this.tecnico = tecnico;

        // Vincular la relación inversa
        if (tecnico != null && tecnico.getValoracionTecnico() != this) {
            tecnico.setValoracionTecnico(this);
        }
    }

}


