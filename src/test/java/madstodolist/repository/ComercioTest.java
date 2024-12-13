// test/repository/ComercioTest.java

package madstodolist.repository;

import madstodolist.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class ComercioTest {

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    @Autowired
    private TarjetaPagoRepository tarjetaPagoRepository;

    // Métodos auxiliares para reducir duplicación

    private Comercio crearYGuardarComercio(String cif) {
        Pais pais = new Pais("default-country");
        paisRepository.save(pais);

        Comercio comercio = new Comercio(cif);
        comercio.setPais_id(pais);
        comercioRepository.save(comercio);

        TipoUsuario tipoUsuario = new TipoUsuario("default-type");
        tipoUsuarioRepository.save(tipoUsuario);

        Usuario usuario = new Usuario("default@gmail.com");
        usuario.setTipo(tipoUsuario);
        usuario.setComercio(comercio);
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario("default2@gmail.com");
        usuario2.setTipo(tipoUsuario);
        usuario2.setComercio(comercio);
        usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario("default3@gmail.com");
        usuario3.setTipo(tipoUsuario);
        usuario3.setComercio(comercio);
        usuarioRepository.save(usuario3);

        comercio.getUsuarios().add(usuario);
        comercio.getUsuarios().add(usuario2);
        comercio.getUsuarios().add(usuario3);

        EstadoPago estadoPago = new EstadoPago("default-state");
        estadoPagoRepository.save(estadoPago);

        TarjetaPago tarjetaPago = new TarjetaPago("default");
        tarjetaPagoRepository.save(tarjetaPago);

        Pago pago = new Pago("pago1");
        pago.setComercio(comercio);
        pago.setEstado(estadoPago);
        pago.setTarjetaPago(tarjetaPago);

        Pago pago2 = new Pago("pago2");
        pago2.setComercio(comercio);
        pago2.setEstado(estadoPago);
        pago2.setTarjetaPago(tarjetaPago);

        Pago pago3 = new Pago("pago3");
        pago3.setComercio(comercio);
        pago3.setEstado(estadoPago);
        pago3.setTarjetaPago(tarjetaPago);

        pagoRepository.save(pago);
        pagoRepository.save(pago2);
        pagoRepository.save(pago3);

        comercio.addPago(pago);
        comercio.addPago(pago2);
        comercio.addPago(pago3);

        comercioRepository.save(comercio);

        return comercio;
    }


    //
    // Tests modelo Comercio en memoria, sin la conexión con la BD
    //

    @Test
    public void crearComercio() {
        // GIVEN
        Comercio comercio = new Comercio("CIF123456");

        // THEN
        assertThat(comercio.getNombre()).isEqualTo("default-name");
        assertThat(comercio.getCif()).isEqualTo("CIF123456");
        assertThat(comercio.getPais()).isEqualTo("default-country");
        assertThat(comercio.getProvincia()).isEqualTo("default-province");
        assertThat(comercio.getDireccion()).isEqualTo("default-address");
        assertThat(comercio.getIban()).isEqualTo("default-iban");
        assertThat(comercio.getApiKey()).isEqualTo("default-apiKey");
        assertThat(comercio.getUrl_back()).isEqualTo("default-url_back");
    }

    @Test
    public void comprobarIgualdadComerciosSinId() {
        // GIVEN
        Comercio comercio1 = new Comercio("CIF123456");
        Comercio comercio2 = new Comercio("CIF123456");
        Comercio comercio3 = new Comercio("CIF654321");

        // THEN
        assertThat(comercio1).isEqualTo(comercio2);
        assertThat(comercio1).isNotEqualTo(comercio3);
    }

    @Test
    public void comprobarIgualdadComerciosConId() {
        // GIVEN
        Comercio comercio1 = new Comercio("CIF123456");
        Comercio comercio2 = new Comercio("CIF654321");
        Comercio comercio3 = new Comercio("CIF123433");

        comercio1.setId(1L);
        comercio2.setId(2L);
        comercio3.setId(1L);

        // THEN
        assertThat(comercio1).isEqualTo(comercio3);
        assertThat(comercio1).isNotEqualTo(comercio2);
    }

    //
    // Tests ComercioRepository.
    //

    @Test
    @Transactional
    public void crearYBuscarComercioBaseDatos() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // THEN
        assertThat(comercio.getId()).isNotNull();

        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        assertThat(comercioBD.getNombre()).isEqualTo("default-name");
        assertThat(comercioBD.getCif()).isEqualTo("CIF123456");
    }

    @Test
    @Transactional
    public void buscarComercioPorCif() {
        // GIVEN
        crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioBD = comercioRepository.findByCif("CIF123456").orElse(null);

        // THEN
        assertThat(comercioBD.getNombre()).isEqualTo("default-name");
    }

    @Test
    @Transactional
    public void unComercioTieneUnaListaDeUsuarios() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioRecuperado = comercioRepository.findById(comercio.getId()).orElse(null);

        // THEN
        assertThat(comercioRecuperado.getUsuarios()).hasSize(3);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalModificaLaBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        Usuario usuarioDB = comercioBD.getUsuarios().iterator().next();
        usuarioDB.setNombre("Usuario Uno Modificado");

        usuarioDB = usuarioRepository.findById(usuarioDB.getId()).orElse(null);

        // THEN
        assertThat(usuarioDB.getNombre()).isEqualTo("Usuario Uno Modificado");
    }

    @Test
    @Transactional
    public void unComercioTieneUnaListaDePagos() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioRecuperado = comercioRepository.findById(comercio.getId()).orElse(null);

        // THEN
        assertThat(comercioRecuperado.getPagos()).hasSize(3);
    }

    @Test
    @Transactional
    public void cambioEnLaEntidadEnTransactionalConPagoModificaLaBD() {
        // GIVEN
        Comercio comercio = crearYGuardarComercio("CIF123456");

        // WHEN
        Comercio comercioBD = comercioRepository.findById(comercio.getId()).orElse(null);
        Pago pagoDB = comercioBD.getPagos().iterator().next();
        pagoDB.setTicketExt("Pago Modificado");

        pagoDB = pagoRepository.findById(pagoDB.getId()).orElse(null);

        // THEN
        assertThat(pagoDB.getTicketExt()).isEqualTo("Pago Modificado");
    }

    @Test
    @Transactional
    public void salvarComercioEnBaseDatosConPaisNoBDLanzaExcepcion() {
        // GIVEN

        Comercio comercio = new Comercio("CIF123456");
        Pais pais = new Pais("default-country");

        // WHEN // THEN

        Assertions.assertThrows(Exception.class, () -> {
            comercioRepository.save(comercio);
        });
    }
}
