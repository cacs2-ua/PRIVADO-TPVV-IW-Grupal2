package madstodolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "personas_contacto")
public class PersonaContacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String telefono;

    @NotNull
    private String nombre;

    @NotNull
    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "comercio_id")
    private Comercio comercio;

    public PersonaContacto() {
    }

    public PersonaContacto(String email_ext) {
        this.email = email_ext;
        this.nombre = "default-name";
        this.telefono = "default-phone";
    }

    public PersonaContacto(String email_ext, String nombre_ext, String telefono_ext) {
        this.email = email_ext;
        this.nombre = nombre_ext;
        this.telefono = telefono_ext;
    }

    public Long getId() {
        return id;
    }

    public String getTelefono_ext() {
        return telefono;
    }

    public void setTelefono_ext(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre_ext() {
        return nombre;
    }

    public void setNombre_ext(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail_ext() {
        return email;
    }

    public void setEmail_ext(String email) {
        this.email = email;
    }

    public Comercio getComercio() {
        return comercio;
    }


    public void setComercio(Comercio comercio) {
        if (this.comercio == comercio) {
            return; // No hacer nada si es el mismo comercio
        }

        // Desvincular el comercio anterior si existe
        if (this.comercio != null) {
            this.comercio.setPersonaContacto(null);
        }

        // Asignar el nuevo comercio
        this.comercio = comercio;

        // Vincular la relación inversa
        if (comercio != null && comercio.getPersonaContacto() != this) {
            comercio.setPersonaContacto(this);
        }

    }
}
