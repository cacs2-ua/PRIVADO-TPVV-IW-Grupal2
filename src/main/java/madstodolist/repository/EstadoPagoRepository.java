package madstodolist.repository;


import madstodolist.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoPagoRepository extends JpaRepository<EstadoPago, Long> {
    Optional<EstadoPago> findById(Long id);
}
