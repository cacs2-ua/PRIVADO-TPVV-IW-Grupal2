package tpvv.dto;

import org.springframework.beans.factory.annotation.Autowired;
import tpvv.model.Comercio;
import tpvv.service.ComercioService;
import tpvv.service.UsuarioService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class UsuarioData {

    private Long id;
    private String email;
    private String nombre;
    private String contrasenya;
    private Long tipoId;
    private boolean activo;
    private Timestamp fechaAlta;
    private ComercioData comercio;


    // Getters y setters
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

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public boolean getActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }

    public Timestamp getFechaAlta() { return fechaAlta; }

    public void setFechaAlta(Timestamp fechaAlta) { this.fechaAlta = fechaAlta; }

    public ComercioData getComercio() { return comercio; }

    public void setComercio(ComercioData comercio) { this.comercio = comercio; }






    // equals, hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioData that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
