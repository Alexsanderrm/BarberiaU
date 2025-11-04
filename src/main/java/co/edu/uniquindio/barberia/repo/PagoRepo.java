package co.edu.uniquindio.barberia.repo;

import co.edu.uniquindio.barberia.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepo extends JpaRepository<Pago, Long> {
    boolean existsByCitaId(Long citaId);
}
