// model/Comercio.java

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

    // Relación One-to-Many con Usuario
    @OneToMany(mappedBy = "comercio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Usuario> usuarios = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    private Pais pais_id;

    public Comercio() {}

    public Comercio(String nif) {
        this.cif = nif;
        this.nombre = "default-name";
        this.pais = "default-country";
        this.provincia = "default-province";
        this.direccion = "default-address";
        this.iban = "default-iban";
        this.api_key = "default-api_key";
        this.url_back = "default-url_back";
        this.pais_id = new Pais("default-country");
    }

    public Comercio(String nombre, String cif, String pais, String provincia, String direccion, String iban, String api_key, String url_back) {
        this.nombre = nombre;
        this.cif = cif;
        this.pais = pais;
        this.provincia = provincia;
        this.direccion = direccion;
        this.iban = iban;
        this.api_key = api_key;
        this.url_back = url_back;
        this.pais_id = new Pais("default-country");
    }

    // Métodos Getter y Setter Correctos

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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getUrl_back() {
        return url_back;
    }

    public void setUrl_back(String url_back) {
        this.url_back = url_back;
    }

    public Pais getPais_id() {
        return pais_id;
    }

    public void setPais_id(Pais pais_id) {
        this.pais_id = pais_id;
    }

    // Getter y Setter de la Relación One-to-Many

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void addUsuario(Usuario usuario) {
        if (usuarios.contains(usuario)) return;
        usuarios.add(usuario);
        if (usuario.getComercio() != this) {
            usuario.setComercio(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comercio comercio = (Comercio) o;

        // Si ambos tienen ID, comparamos por ID
        if (id != null && comercio.id != null) {
            return Objects.equals(id, comercio.id);
        }

        // Si no hay ID, comparamos por la clave alternativa (CIF)
        return Objects.equals(cif, comercio.cif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cif);
    }
}
