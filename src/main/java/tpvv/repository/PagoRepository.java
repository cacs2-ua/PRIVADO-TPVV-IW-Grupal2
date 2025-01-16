package tpvv.repository;

import org.springframework.data.jpa.repository.Query;
import tpvv.dto.PagoRecursoData;
import tpvv.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByTicketExt(String ticket);

    List<Pago> findByComercioId(Long comercioId);

    @Query("SELECT COUNT(p) FROM Pago p WHERE p.comercio.id = :comercioId AND p.fecha >= :fechaDesde")
    Long countPagosByComercioAndFechaDesde(Long comercioId, Date fechaDesde);

    @Query("SELECT COUNT(p) FROM Pago p WHERE p.fecha >= :fechaDesde")
    Long countPagosByFechaDesde(Date fechaDesde);

}
