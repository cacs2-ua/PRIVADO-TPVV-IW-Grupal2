// repository/ComercioRepository.java

package madstodolist.repository;

import madstodolist.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    Optional<Mensaje> findById(Long id);
}
