// repository/ComercioRepository.java

package madstodolist.repository;

import madstodolist.model.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComercioRepository extends JpaRepository<Comercio, Long> {
    Optional<Comercio> findByCif(String cif);
}
