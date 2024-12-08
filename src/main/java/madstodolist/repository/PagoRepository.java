package madstodolist.repository;

import madstodolist.model.Comercio;
import madstodolist.model.Incidencia;
import madstodolist.model.Mensaje;
import madstodolist.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findById(Long id);
}
