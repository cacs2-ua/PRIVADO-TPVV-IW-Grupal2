package madstodolist.repository;

import madstodolist.model.Incidencia;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {
    Optional<Incidencia> findById(Long id);
}
