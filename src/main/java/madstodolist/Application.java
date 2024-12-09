package madstodolist;

import madstodolist.model.Comercio;
import madstodolist.model.Pais;
import madstodolist.repository.ComercioRepository;
import madstodolist.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private PaisRepository paisRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Clase de inicialización para crear un Comercio y un Pais de ejemplo
    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // Verificar si el país ya existe
            Pais pais = paisRepository.findByNombre("España")
                    .orElseGet(() -> {
                        Pais nuevoPais = new Pais("España");
                        return paisRepository.save(nuevoPais); // Guardar el país si no existe
                    });

            // Verificar si el comercio ya existe
            if (!comercioRepository.findByCif("CIF123456").isPresent()) {
                Comercio comercio = new Comercio("default");
                comercio.setNombre("Comercio Ejemplo");
                comercio.setCif("CIF123456");
                comercio.setPais("España");
                comercio.setProvincia("Madrid");
                comercio.setDireccion("Calle Falsa 123");
                comercio.setIban("ES9121000418450200051332");
                comercio.setApiKey("mi-api-key-12345"); // Genera una API-Key segura en producción
                comercio.setUrl_back("https://comercio-ejemplo.com/back");
                comercio.setPais_id(pais); // Asignar el país al comercio
                comercioRepository.save(comercio);
                System.out.println("Comercio de ejemplo creado con API-Key: mi-api-key-12345");
            }
        };
    }
}
