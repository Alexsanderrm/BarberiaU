package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.CrearCitaDTO;
import co.edu.uniquindio.barberia.domain.*;
import co.edu.uniquindio.barberia.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {
    private final CitaRepo citaRepo;
    private final ClienteRepo clienteRepo;
    private final BarberoRepo barberoRepo;

    public CitaService(CitaRepo c, ClienteRepo cl, BarberoRepo b){
        this.citaRepo = c; this.clienteRepo = cl; this.barberoRepo = b;
    }

    @Transactional
    public Cita agendar(CrearCitaDTO dto){
        var cliente = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));
        var barbero = barberoRepo.findById(dto.barberoId())
                .orElseThrow(() -> new IllegalArgumentException("Barbero no existe"));

        // Conflictos (barbero y cliente)
        if (citaRepo.existsByBarberoIdAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
                dto.barberoId(), dto.fechaHoraFin(), dto.fechaHoraInicio()))
            throw new IllegalArgumentException("Barbero ocupado en ese horario");

        if (citaRepo.existsByClienteIdAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
                dto.clienteId(), dto.fechaHoraFin(), dto.fechaHoraInicio()))
            throw new IllegalArgumentException("Cliente con otra cita en ese horario");

        var cita = Cita.builder()
                .cliente(cliente).barbero(barbero)
                .fechaHoraInicio(dto.fechaHoraInicio())
                .fechaHoraFin(dto.fechaHoraFin())
                .estado(Cita.Estado.AGENDADA)
                .build();
        return citaRepo.save(cita);
    }

    @Transactional
    public void cancelar(Long citaId, String motivo){
        var cita = citaRepo.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no existe"));
        if (cita.getEstado() != Cita.Estado.AGENDADA)
            throw new IllegalStateException("Solo se cancelan citas Agendadas");
        cita.setEstado(Cita.Estado.CANCELADA);
        // (Opcional) guardar el motivo en otra tabla
    }
}
