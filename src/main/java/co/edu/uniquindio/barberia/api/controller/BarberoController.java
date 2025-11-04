package co.edu.uniquindio.barberia.api.controller;

import co.edu.uniquindio.barberia.api.dto.ActualizarDisponibilidadDTO;
import co.edu.uniquindio.barberia.api.dto.CrearBarberoDTO;
import co.edu.uniquindio.barberia.api.dto.HorarioDTO;
import co.edu.uniquindio.barberia.domain.Barbero;
import co.edu.uniquindio.barberia.domain.HorarioBarbero;
import co.edu.uniquindio.barberia.repo.CitaRepo;
import co.edu.uniquindio.barberia.service.BarberoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/barberos")
public class BarberoController {

    private final BarberoService service;
    private final CitaRepo citaRepo;

    public BarberoController(BarberoService service, CitaRepo citaRepo) {
        this.service = service;
        this.citaRepo = citaRepo;
    }

    @GetMapping("/ping")
    public String ping() {
        return "barberos-ok";
    }

    // POST /api/barberos
    @PostMapping
    public ResponseEntity<Barbero> crear(@Valid @RequestBody CrearBarberoDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    // GET /api/barberos
    @GetMapping
    public List<Barbero> listar() {
        return service.listar();
    }

    // POST /api/barberos/{id}/horarios
    @PostMapping("/{id}/horarios")
    public ResponseEntity<HorarioBarbero> agregarHorario(
            @PathVariable Long id,
            @Valid @RequestBody HorarioDTO dto) {
        return ResponseEntity.ok(service.agregarHorario(id, dto));
    }

    // PUT /api/barberos/disponibilidad
    @PutMapping("/disponibilidad")
    public ResponseEntity<Barbero> actualizarDisponibilidad(
            @Valid @RequestBody ActualizarDisponibilidadDTO dto) {
        return ResponseEntity.ok(service.actualizarDisponibilidad(dto));
    }

    // GET /api/barberos/{id}/agenda?dia=2025-11-03
    @GetMapping("/{id}/agenda")
    public ResponseEntity<?> consultarAgenda(
            @PathVariable Long id,
            @RequestParam LocalDate dia) {

        LocalDateTime desde = dia.atStartOfDay();
        LocalDateTime hasta = dia.plusDays(1).atStartOfDay().minusSeconds(1);

        var citas = citaRepo.findByBarberoIdAndFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
                id, desde, hasta
        );

        return ResponseEntity.ok(citas);
    }
}
