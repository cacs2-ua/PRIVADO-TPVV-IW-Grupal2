package madstodolist.repository;

import madstodolist.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    Optional<Pais> findById(Long id);
    Optional<Pais> findByNombre(String nombre);
}

