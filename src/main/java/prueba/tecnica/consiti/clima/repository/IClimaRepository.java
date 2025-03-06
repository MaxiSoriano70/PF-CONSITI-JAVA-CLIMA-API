package prueba.tecnica.consiti.clima.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import prueba.tecnica.consiti.clima.entity.Clima;
import java.util.Optional;

public interface IClimaRepository extends JpaRepository<Clima, Integer> {
    Optional<Clima> findByCiudad(String ciudad);
}
