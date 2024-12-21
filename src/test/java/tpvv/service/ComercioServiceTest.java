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
        String nombre = "Comercio Test";
        String cif = "A12345678";
        String pais = "España";
        String provincia = "Madrid";
        String direccion = "Calle Falsa 123";
        String iban = "ES6621000418401234567891";
        String apiKey = "clave-api";
        String url_back = "http://callback.test";

        if (paisRepository.findByNombre(pais).isEmpty()) {
            Pais nuevoPais = new Pais(pais);
            paisRepository.save(nuevoPais);
        }


        ComercioData comercio = comercioService.crearComercio(nombre, cif, pais, provincia, direccion, iban, apiKey, url_back);
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

}
