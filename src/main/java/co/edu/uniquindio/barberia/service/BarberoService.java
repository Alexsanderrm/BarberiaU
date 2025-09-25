package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.CrearBarberoDTO;
import co.edu.uniquindio.barberia.api.dto.HorarioDTO;
import co.edu.uniquindio.barberia.domain.Barbero;
import co.edu.uniquindio.barberia.domain.HorarioBarbero;
import co.edu.uniquindio.barberia.repo.BarberoRepo;
import co.edu.uniquindio.barberia.repo.HorarioBarberoRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarberoService {
    private final BarberoRepo barberoRepo;
    private final HorarioBarberoRepo horarioRepo;

    public BarberoService(BarberoRepo b, HorarioBarberoRepo h) {
        this.barberoRepo = b; this.horarioRepo = h;
    }

    @Transactional
    public Barbero crear(CrearBarberoDTO dto) {
        Barbero b = Barbero.builder()
                .nombre(dto.nombre())
                .especialidad(dto.especialidad())
                .telefono(dto.telefono())
                .activo(true)
                .build();
        return barberoRepo.save(b);
    }

    @Transactional
    public HorarioBarbero agregarHorario(Long barberoId, HorarioDTO dto) {
        var b = barberoRepo.findById(barberoId)
                .orElseThrow(() -> new IllegalArgumentException("Barbero no existe"));

        // Validación simple de solapamiento en el mismo día
        var existentes = horarioRepo.findByBarberoIdAndDiaSemana(barberoId, dto.diaSemana());
        boolean solapa = existentes.stream().anyMatch(h ->
                !(dto.horaFin().isBefore(h.getHoraInicio()) || dto.horaInicio().isAfter(h.getHoraFin()))
        );
        if (solapa) throw new IllegalArgumentException("Horario solapa con otro existente");

        var h = HorarioBarbero.builder()
                .barbero(b)
                .diaSemana(dto.diaSemana())
                .horaInicio(dto.horaInicio())
                .horaFin(dto.horaFin())
                .build();
        return horarioRepo.save(h);
    }
    // src/main/java/co/edu/uniquindio/barberia/service/BarberoService.java
    public java.util.List<Barbero> listar() { return barberoRepo.findAll();
    }

}
