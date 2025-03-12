package prueba.tecnica.consiti.clima.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import prueba.tecnica.consiti.clima.entity.Consulta;
import java.util.Optional;

public interface IClimaRepository extends JpaRepository<Consulta, Integer> {
    Optional<Consulta> findByCiudad(String ciudad);
}
