package co.edu.uniquindio.barberia.api.controller;

import co.edu.uniquindio.barberia.api.dto.CrearCitaDTO;
import co.edu.uniquindio.barberia.domain.Cita;
import co.edu.uniquindio.barberia.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService service;

    /**
     * POST /api/citas
     * Crea una nueva cita indicando cliente, barbero, servicio y rango de horas
     */
    @PostMapping
    public ResponseEntity<Cita> crear(@Valid @RequestBody CrearCitaDTO dto) {
        return ResponseEntity.ok(service.agendar(dto));
    }

    /**
     * GET /api/citas
     * Lista todas las citas con cliente y barbero
     */
    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /**
     * DELETE /api/citas/{id}?motivo=...
     * Cancela una cita (solo si está AGENDADA)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "sin_motivo") String motivo) {
        service.cancelar(id, motivo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "citas-ok";
    }
}
