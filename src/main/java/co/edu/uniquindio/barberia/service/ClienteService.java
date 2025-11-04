package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.CrearClienteDTO;
import co.edu.uniquindio.barberia.domain.Cliente;
import co.edu.uniquindio.barberia.repo.ClienteRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepo repo;

    public ClienteService(ClienteRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public Cliente crear(CrearClienteDTO dto) {
        if (repo.existsByDocumentoOrTelefono(dto.documento(), dto.telefono())) {
            throw new IllegalArgumentException("Cliente ya existe (documento o teléfono)");
        }

        Cliente cliente = Cliente.builder()
                .nombre(dto.nombre())
                .documento(dto.documento())
                .telefono(dto.telefono())
                .build();

        return repo.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repo.findAll();
    }
}
