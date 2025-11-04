package co.edu.uniquindio.barberia.api.controller;

import co.edu.uniquindio.barberia.api.dto.CrearClienteDTO;
import co.edu.uniquindio.barberia.domain.Cliente;
import co.edu.uniquindio.barberia.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // POST /api/clientes
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody CrearClienteDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    // GET /api/clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /api/clientes/ping
    @GetMapping("/ping")
    public String ping() {
        return "clientes-ok";
    }
}
