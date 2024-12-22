package tpvv.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private PaisSeeder paisSeeder;

    // Puedes inyectar más seeders aquí en el futuro

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equalsIgnoreCase("seed")) {
            paisSeeder.seed();
            // Llama a otros seeders aquí si los tienes
            System.out.println("Seeder general ejecutado correctamente.");
        }
    }
}
