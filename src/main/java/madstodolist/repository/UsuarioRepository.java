// repository/UsuarioRepository.java

package madstodolist.repository;

import madstodolist.model.Incidencia;
import madstodolist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
}
