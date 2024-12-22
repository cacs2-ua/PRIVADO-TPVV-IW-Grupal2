package tpvv.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tpvv.model.Pais;
import tpvv.repository.PaisRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class PaisSeeder {

    @Autowired
    private PaisRepository paisRepository;

    public void seed() {
        // Eliminar todos los países existentes
        paisRepository.deleteAll();

        // Crear lista de 10 países
        List<Pais> paises = Arrays.asList(
                new Pais("España"),
                new Pais("Francia"),
                new Pais("Alemania"),
                new Pais("Italia"),
                new Pais("Portugal"),
                new Pais("Reino Unido"),
                new Pais("Países Bajos"),
                new Pais("Bélgica"),
                new Pais("Suiza"),
                new Pais("Suecia")
        );

        // Guardar los nuevos países en la base de datos
        paisRepository.saveAll(paises);

        System.out.println("Seeder Pais ejecutado correctamente.");
    }
}
