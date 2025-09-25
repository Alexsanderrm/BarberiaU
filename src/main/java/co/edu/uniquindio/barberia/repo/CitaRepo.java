package co.edu.uniquindio.barberia.repo;

import co.edu.uniquindio.barberia.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepo extends JpaRepository<Cita, Long> {

    // ¿El barbero tiene una cita que se solape con el rango dado?
    boolean existsByBarberoIdAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
            Long barberoId,
            LocalDateTime end,
            LocalDateTime start
    );

    // ¿El cliente tiene una cita que se solape con el rango dado?
    boolean existsByClienteIdAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
            Long clienteId,
            LocalDateTime end,
            LocalDateTime start
    );

    // Listar citas del barbero en un rango (ordenadas por inicio)
    List<Cita> findByBarberoIdAndFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
            Long barberoId,
            LocalDateTime desde,
            LocalDateTime hasta
    );
}
