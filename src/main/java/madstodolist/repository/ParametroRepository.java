package madstodolist.repository;

import madstodolist.model.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParametroRepository extends JpaRepository<Parametro, Long> {
    Optional<Parametro> findById(Long id);
    Optional<Parametro> findByNombre(String nombre);
}
