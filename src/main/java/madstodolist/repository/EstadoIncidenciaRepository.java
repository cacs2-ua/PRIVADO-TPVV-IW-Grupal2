package madstodolist.repository;

import madstodolist.model.EstadoIncidencia;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EstadoIncidenciaRepository extends CrudRepository<EstadoIncidencia, Long> {
    Optional<EstadoIncidencia> findById(Long id);
}
