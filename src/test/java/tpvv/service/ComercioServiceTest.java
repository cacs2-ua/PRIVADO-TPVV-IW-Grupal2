package tpvv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tpvv.dto.ComercioData;
import tpvv.model.Comercio;
import tpvv.model.Pais;
import tpvv.repository.PaisRepository;


@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class ComercioServiceTest {

    @Autowired
    ComercioService comercioService;

    @Autowired
    PaisRepository paisRepository;

    private ComercioData crearComercio() {
        ComercioData nuevoComercio = new ComercioData();
        Pais nuevoPais = new Pais("España");

        if (paisRepository.findByNombre("España").isEmpty()) {
            paisRepository.save(nuevoPais);
        }

        nuevoComercio.setNombre("Comercio Test");
        nuevoComercio.setCif("A12345678");
        nuevoComercio.setPais("España");
        nuevoComercio.setProvincia("Madrid");
        nuevoComercio.setDireccion("Calle Falsa 123");
        nuevoComercio.setIban("ES6621000418401234567891");
        nuevoComercio.setUrl_back("http://callback.test");


        ComercioData comercio = comercioService.crearComercio(nuevoComercio);
        return comercio;
    }

    @Test
    public void crearRecuperarComercioTest() {
        ComercioData comercio = crearComercio();
        assertThat(comercio.getId()).isNotNull();

        ComercioData comercioBd = comercioService.recuperarComercio(comercio.getId());
        assertThat(comercioBd).isNotNull();
        assertThat(comercioBd.getNombre()).isEqualTo("Comercio Test");
    }

    @Test
    public void actualizarURLComercioTest() {
        String nuevaURL = "https://www.google.com/";
        ComercioData comercio = crearComercio();
        comercioService.actualizarURLComercio(comercio.getId(), nuevaURL);
        ComercioData comercioActualizado = comercioService.recuperarComercio(comercio.getId());
        assertThat(comercioActualizado.getUrl_back())
                .isEqualTo(nuevaURL);
    }

    @Test
    public void regenerarAPIKeyComercioTest() {
        ComercioData comercio = crearComercio();
        String antiguaApi = comercio.getApiKey();
        comercioService.regenerarAPIKeyComercio(comercio.getId());
        ComercioData comercioActualizado = comercioService.recuperarComercio(comercio.getId());
        assertThat(comercioActualizado.getApiKey())
                .isNotEqualTo(antiguaApi);
    }

}
