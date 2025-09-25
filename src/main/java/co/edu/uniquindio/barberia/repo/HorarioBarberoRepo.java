package co.edu.uniquindio.barberia.repo;

import co.edu.uniquindio.barberia.domain.HorarioBarbero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioBarberoRepo extends JpaRepository<HorarioBarbero, Long> {
    List<HorarioBarbero> findByBarberoIdAndDiaSemana(Long barberoId, int diaSemana);
}
