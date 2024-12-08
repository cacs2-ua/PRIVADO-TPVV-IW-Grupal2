package madstodolist.repository;

import madstodolist.model.ValoracionTecnico;
import org.w3c.dom.Text;

import madstodolist.model.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ValoracionTecnicoRepository extends JpaRepository<ValoracionTecnico, Long> {
    Optional<ValoracionTecnico> findById(Long id);
}

