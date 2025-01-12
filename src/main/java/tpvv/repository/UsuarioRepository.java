// repository/UsuarioRepository.java

package tpvv.repository;

import tpvv.model.Comercio;
import tpvv.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByComercio(Comercio comercio);
    Optional<Usuario> findUsuarioById(Long id);
}
