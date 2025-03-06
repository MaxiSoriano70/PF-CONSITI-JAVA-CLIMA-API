package prueba.tecnica.consiti.clima.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import prueba.tecnica.consiti.clima.security.entity.Usuario;
import java.util.Optional;
public interface IUsuarioRepository extends JpaRepository <Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
