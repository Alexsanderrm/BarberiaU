package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.ActualizarServicioDTO;
import co.edu.uniquindio.barberia.api.dto.CrearServicioDTO;
import co.edu.uniquindio.barberia.domain.Servicio;
import co.edu.uniquindio.barberia.repo.ServicioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepo repo;

    @Transactional
    public Servicio crear(CrearServicioDTO dto) {
        if (repo.findByNombreIgnoreCase(dto.nombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un servicio con ese nombre");
        }

        Servicio servicio = Servicio.builder()
                .nombre(dto.nombre())
                .precio(dto.precio())
                .activo(true)
                .build();

        return repo.save(servicio);
    }

    @Transactional
    public Servicio actualizar(ActualizarServicioDTO dto) {
        Servicio servicio = repo.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        if (dto.nombre() != null && !dto.nombre().isBlank()) {
            servicio.setNombre(dto.nombre());
        }
        if (dto.precio() >= 0) {
            servicio.setPrecio(dto.precio());
        }

        return repo.save(servicio);
    }

    @Transactional(readOnly = true)
    public List<Servicio> listar() {
        return repo.findAll();
    }

    @Transactional
    public void eliminar(Long id) {
        Servicio servicio = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        servicio.setActivo(false);
        repo.save(servicio);
    }
}
