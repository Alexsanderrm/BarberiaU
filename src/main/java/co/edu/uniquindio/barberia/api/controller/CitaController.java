package co.edu.uniquindio.barberia.api.controller;

import co.edu.uniquindio.barberia.api.dto.CrearCitaDTO;
import co.edu.uniquindio.barberia.domain.Cita;
import co.edu.uniquindio.barberia.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final CitaService service;
    public CitaController(CitaService s){ this.service = s; }

    @PostMapping
    public ResponseEntity<Cita> crear(@Valid @RequestBody CrearCitaDTO dto){
        return ResponseEntity.ok(service.agendar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id,
                                         @RequestParam(defaultValue = "N/A") String motivo){
        service.cancelar(id, motivo);
        return ResponseEntity.noContent().build();
    }
}
