package tpvv.repository;

import tpvv.model.Incidencia;
import org.springframework.data.repository.CrudRepository;
import tpvv.model.Pago;

import java.util.List;

public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {
    List<Incidencia> findIncidenciasByUsuarioComercio_Id(Long usuario_comercio_id);
}
