package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    public ValoracionTecnico() {}

    public ValoracionTecnico(float valoracion) {
        this.valoracion = valoracion;
    }

}


