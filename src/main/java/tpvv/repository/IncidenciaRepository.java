package tpvv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tpvv.model.Incidencia;

import java.util.List;
import java.util.Optional;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findIncidenciasByUsuarioComercio_Id(Long usuario_comercio_id);
    Optional<Incidencia> findIncidenciaById(Long id);
}
