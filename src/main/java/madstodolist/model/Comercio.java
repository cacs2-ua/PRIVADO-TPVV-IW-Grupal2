package madstodolist.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comercios")
public class Comercio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    @Column(unique = true)
    private String cif;

    @NotNull
    private String pais;

    @NotNull
    private String provincia;

    @NotNull
    private String direccion;

    @NotNull
    private String iban;

    @NotNull
    private String api_key;

    @NotNull
    private String url_back;

    public Comercio() {}

    public Comercio(String nombre, String cif, String pais, String provincia, String direccion, String iban, String api_key, String url_back) {
        this.nombre = nombre;
        this.cif = cif;
        this.pais = pais;
        this.provincia = provincia;
        this.direccion = direccion;
        this.iban = iban;
        this.api_key = api_key;
        this.url_back = url_back;
    }


    public void getNombre(String nombre) {
        this.nombre = nombre;
    }

    public String setNombre() {
        return nombre;
    }

    public void getCif(String cif) {
        this.cif = cif;
    }

    public String setCif() {
        return cif;
    }

    public void getPais(String pais) {
        this.pais = pais;
    }

    public String setPais() {
        return pais;
    }

    public void getProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String setProvincia() {
        return provincia;
    }

    public void getDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String setDireccion() {
        return direccion;
    }

    public void getIban(String iban) {
        this.iban = iban;
    }

    public String setIban() {
        return iban;
    }

    public void getApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String setApi_key() {
        return api_key;
    }

    public void getUrl_back(String url_back) {
        this.url_back = url_back;
    }

    public String setUrl_back() {
        return url_back;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comercio comercio = (Comercio) o;
        return Objects.equals(id, comercio.id) ||
                Objects.equals(cif, comercio.cif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cif, nombre, pais, provincia, direccion, iban, api_key, url_back);
    }

}
