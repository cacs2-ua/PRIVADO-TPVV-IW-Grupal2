// repository/UsuarioRepository.java

package madstodolist.repository;

import madstodolist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);
}
