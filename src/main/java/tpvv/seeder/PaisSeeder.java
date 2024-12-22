package tpvv.seeder;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tpvv.model.Pais;
import tpvv.repository.PaisRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Component
public class PaisSeeder {

    @Autowired
    private PaisRepository paisRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void seed() {
        // Eliminar todos los países existentes
        paisRepository.deleteAll();
        entityManager.flush(); // Asegurar que las eliminaciones se persistan

        // Reiniciar la secuencia de IDs
        resetSequence();

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

    private void resetSequence() {
        try {
            // Consulta para obtener el nombre de la secuencia asociada a la tabla 'paises'
            String sequenceNameQuery = "SELECT pg_get_serial_sequence('paises', 'id')";
            Query sequenceQuery = entityManager.createNativeQuery(sequenceNameQuery);
            String sequenceName = (String) sequenceQuery.getSingleResult();

            // Reinicia la secuencia para empezar desde 1
            String resetQuery = "ALTER SEQUENCE " + sequenceName + " RESTART WITH 1";
            entityManager.createNativeQuery(resetQuery).executeUpdate();

            System.out.println("Secuencia " + sequenceName + " reiniciada a 1.");
        } catch (Exception e) {
            System.err.println("Error al reiniciar la secuencia: " + e.getMessage());
        }
    }
}
