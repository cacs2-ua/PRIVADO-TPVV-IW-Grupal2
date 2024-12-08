package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.Incidencia;
import madstodolist.model.Mensaje;
import madstodolist.model.TarjetaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TarjetaPagoRepository extends JpaRepository<TarjetaPago, Long> {
    Optional<TarjetaPago> findById(Long id);
    Optional<TarjetaPago> findByNumeroTarjeta(String numeroTarjeta);
}
