package co.edu.uniquindio.barberia.api.controller;

import co.edu.uniquindio.barberia.api.dto.RegistrarPagoDTO;
import co.edu.uniquindio.barberia.domain.Pago;
import co.edu.uniquindio.barberia.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @PostMapping
    public ResponseEntity<Pago> registrar(@RequestBody RegistrarPagoDTO dto) {
        return ResponseEntity.ok(service.registrar(dto));
    }
}
