package co.edu.uniquindio.barberia.api.controller;

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
    private final CitaRepo citaRepo;                                // <-- inyecta el repo


    public BarberoController(BarberoService service, CitaRepo citaRepo) {
        this.service = service;
        this.citaRepo = citaRepo;
    }

    // Comprobación rápida de que el controlador está cargado
    @GetMapping("/ping")
    public String ping() {
        return "barberos-ok";
    }

    // Crear barbero
    @PostMapping
    public ResponseEntity<Barbero> crear(@Valid @RequestBody CrearBarberoDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    // Listar barberos (útil para ver IDs)
    @GetMapping
    public List<Barbero> listar() {
        return service.listar();
    }

    // Agregar un horario al barbero {id}
    @PostMapping("/{id}/horarios")
    public ResponseEntity<HorarioBarbero> agregarHorario(@PathVariable Long id,
                                                         @Valid @RequestBody HorarioDTO dto) {
        return ResponseEntity.ok(service.agregarHorario(id, dto));
    }
    // GET /api/barberos/{id}/agenda?dia=2025-09-22
    @GetMapping("/{id}/agenda")
    public ResponseEntity<?> agenda(@PathVariable Long id,
                                    @RequestParam LocalDate dia) {  // Spring parsea 'YYYY-MM-DD'
        LocalDateTime desde = dia.atStartOfDay();
        LocalDateTime hasta = dia.plusDays(1).atStartOfDay().minusSeconds(1);

        var citas = citaRepo.findByBarberoIdAndFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
                id, desde, hasta
        );
        return ResponseEntity.ok(citas);
    }
}
