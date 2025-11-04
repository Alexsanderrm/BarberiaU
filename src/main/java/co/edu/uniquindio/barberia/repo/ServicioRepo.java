package co.edu.uniquindio.barberia.repo;

import co.edu.uniquindio.barberia.domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicioRepo extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByNombreIgnoreCase(String nombre);
}
