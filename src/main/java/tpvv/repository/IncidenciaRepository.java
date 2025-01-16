package tpvv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tpvv.dto.UsuarioData;
import tpvv.model.Incidencia;
import tpvv.model.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findIncidenciasByUsuarioComercio_Id(Long usuario_comercio_id);
    Optional<Incidencia> findIncidenciaById(Long id);

    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.usuarioComercio.id IN :usuarioIds AND i.fecha >= :fechaDesde")
    Long countIncidenciasByUsuarioIdsAndFechaDesde(List<Long> usuarioIds, Date fechaDesde);

    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.usuarioComercio.id IN :usuarioIds AND i.fecha >= :fechaDesde AND i.estado.id = :estadoId")
    Long countIncidenciasByUsuarioIdsFechaDesdeAndEstado(List<Long> usuarioIds, Date fechaDesde, int estadoId);

}
