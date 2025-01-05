package tpvv.repository;

import tpvv.model.EstadoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import tpvv.model.EstadoPago;

import java.util.Optional;

public interface EstadoIncidenciaRepository extends JpaRepository<EstadoIncidencia, Long> {
    Optional<EstadoIncidencia> findByNombre(String nombre);
}
