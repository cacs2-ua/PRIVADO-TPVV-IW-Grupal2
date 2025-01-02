package tpvv.dto;

import java.util.Objects;

public class PersonaContactoData {

    private Long id;
    private String telefono;
    private String nombreContacto;
    private String email;

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // equals, hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonaContactoData that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
