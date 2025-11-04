package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.ActualizarDisponibilidadDTO;
import co.edu.uniquindio.barberia.api.dto.CrearBarberoDTO;
import co.edu.uniquindio.barberia.api.dto.HorarioDTO;
import co.edu.uniquindio.barberia.domain.Barbero;
import co.edu.uniquindio.barberia.domain.HorarioBarbero;
import co.edu.uniquindio.barberia.repo.BarberoRepo;
import co.edu.uniquindio.barberia.repo.HorarioBarberoRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class BarberoService {

    private final BarberoRepo barberoRepo;
    private final HorarioBarberoRepo horarioRepo;

    public BarberoService(BarberoRepo barberoRepo, HorarioBarberoRepo horarioRepo) {
        this.barberoRepo = barberoRepo;
        this.horarioRepo = horarioRepo;
    }

    @Transactional
    public Barbero crear(CrearBarberoDTO dto) {
        Barbero barbero = Barbero.builder()
                .nombre(dto.nombre())
                .especialidad(dto.especialidad())
                .telefono(dto.telefono())
                .activo(true)
                .build();
        return barberoRepo.save(barbero);
    }

    @Transactional
    public HorarioBarbero agregarHorario(Long barberoId, HorarioDTO dto) {
        Barbero barbero = barberoRepo.findById(barberoId)
                .orElseThrow(() -> new IllegalArgumentException("Barbero no existe"));

        if (!barbero.isActivo()) {
            throw new IllegalStateException("No se pueden agregar horarios a un barbero inactivo");
        }

        if (dto.horaFin().isBefore(dto.horaInicio()) || dto.horaFin().equals(dto.horaInicio())) {
            throw new IllegalArgumentException("Hora fin debe ser posterior a hora inicio");
        }

        var existentes = horarioRepo.findByBarberoIdAndDiaSemana(barberoId, dto.diaSemana());
        boolean solapa = existentes.stream().anyMatch(h ->
                !(dto.horaFin().isBefore(h.getHoraInicio()) || dto.horaInicio().isAfter(h.getHoraFin()))
        );

        if (solapa) {
            throw new IllegalArgumentException("El horario se solapa con otro existente");
        }

        HorarioBarbero horario = HorarioBarbero.builder()
                .barbero(barbero)
                .diaSemana(dto.diaSemana())
                .horaInicio(dto.horaInicio())
                .horaFin(dto.horaFin())
                .build();

        return horarioRepo.save(horario);
    }

    @Transactional(readOnly = true)
    public List<Barbero> listar() {
        return barberoRepo.findAll();
    }

    @Transactional
    public Barbero actualizarDisponibilidad(ActualizarDisponibilidadDTO dto) {
        Barbero barbero = barberoRepo.findById(dto.idBarbero())
                .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado"));

        if (!barbero.isActivo()) {
            throw new IllegalStateException("El barbero está inactivo");
        }

        // eliminar horarios existentes
        List<HorarioBarbero> horariosActuales = horarioRepo.findAll().stream()
                .filter(h -> h.getBarbero().getId().equals(barbero.getId()))
                .toList();
        if (!horariosActuales.isEmpty()) {
            horarioRepo.deleteAll(horariosActuales);
        }

        // registrar nuevos horarios
        dto.horariosDisponibles().forEach(h -> {
            HorarioBarbero nuevo = HorarioBarbero.builder()
                    .barbero(barbero)
                    .diaSemana(h.diaSemana())
                    .horaInicio(LocalTime.parse(h.horaInicio()))
                    .horaFin(LocalTime.parse(h.horaFin()))
                    .build();
            horarioRepo.save(nuevo);
        });

        return barbero;
    }
}
