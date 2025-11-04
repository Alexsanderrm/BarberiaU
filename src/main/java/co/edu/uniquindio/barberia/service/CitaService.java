package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.CrearCitaDTO;
import co.edu.uniquindio.barberia.domain.*;
import co.edu.uniquindio.barberia.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepo citaRepo;
    private final ClienteRepo clienteRepo;
    private final BarberoRepo barberoRepo;
    private final HorarioBarberoRepo horarioRepo;
    private final ServicioRepo servicioRepo;

    @Transactional
    public Cita agendar(CrearCitaDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));
        Barbero barbero = barberoRepo.findById(dto.barberoId())
                .orElseThrow(() -> new IllegalArgumentException("Barbero no existe"));
        Servicio servicio = servicioRepo.findById(dto.servicioId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no existe"));

        if (!barbero.isActivo()) {
            throw new IllegalStateException("El barbero está inactivo");
        }
        if (!servicio.isActivo()) {
            throw new IllegalStateException("El servicio no está disponible actualmente");
        }

        // Validar horario del barbero
        int diaSemana = dto.fechaHoraInicio().getDayOfWeek().getValue();
        var horarios = horarioRepo.findByBarberoIdAndDiaSemana(barbero.getId(), diaSemana);

        boolean dentroHorario = horarios.stream().anyMatch(h ->
                !dto.fechaHoraInicio().toLocalTime().isBefore(h.getHoraInicio()) &&
                        !dto.fechaHoraFin().toLocalTime().isAfter(h.getHoraFin())
        );
        if (!dentroHorario) {
            throw new IllegalArgumentException("El barbero no trabaja en ese horario");
        }

        // Validar solapamiento de citas
        if (citaRepo.existsByBarberoIdAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
                barbero.getId(), dto.fechaHoraFin(), dto.fechaHoraInicio())) {
            throw new IllegalArgumentException("El barbero tiene otra cita en ese horario");
        }
        if (citaRepo.existsByClienteIdAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
                cliente.getId(), dto.fechaHoraFin(), dto.fechaHoraInicio())) {
            throw new IllegalArgumentException("El cliente tiene otra cita en ese horario");
        }

        Cita cita = Cita.builder()
                .cliente(cliente)
                .barbero(barbero)
                .servicio(servicio)
                .fechaHoraInicio(dto.fechaHoraInicio())
                .fechaHoraFin(dto.fechaHoraFin())
                .estado(Cita.Estado.AGENDADA)
                .build();

        return citaRepo.save(cita);
    }

    @Transactional
    public void cancelar(Long id, String motivo) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no existe"));
        if (cita.getEstado() != Cita.Estado.AGENDADA)
            throw new IllegalStateException("Solo se pueden cancelar citas AGENDADAS");
        cita.setEstado(Cita.Estado.CANCELADA);
        citaRepo.save(cita);
    }

    @Transactional(readOnly = true)
    public List<Cita> listar() {
        return citaRepo.findAllWithClienteAndBarbero();
    }
}
