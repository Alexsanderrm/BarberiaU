package co.edu.uniquindio.barberia.api.controller;

import co.edu.uniquindio.barberia.api.dto.ActualizarServicioDTO;
import co.edu.uniquindio.barberia.api.dto.CrearServicioDTO;
import co.edu.uniquindio.barberia.domain.Servicio;
import co.edu.uniquindio.barberia.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService service;

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody CrearServicioDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping
    public ResponseEntity<Servicio> actualizar(@RequestBody ActualizarServicioDTO dto) {
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Servicio>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
