package co.edu.uniquindio.barberia.service;

import co.edu.uniquindio.barberia.api.dto.RegistrarPagoDTO;
import co.edu.uniquindio.barberia.domain.Cita;
import co.edu.uniquindio.barberia.domain.Cita.Estado;
import co.edu.uniquindio.barberia.domain.Pago;
import co.edu.uniquindio.barberia.repo.CitaRepo;
import co.edu.uniquindio.barberia.repo.PagoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final CitaRepo citaRepo;
    private final PagoRepo pagoRepo;

    @Transactional
    public Pago registrar(RegistrarPagoDTO dto) {
        Cita cita = citaRepo.findById(dto.idCita())
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        if (cita.getEstado() == Estado.CANCELADA) {
            throw new IllegalStateException("No se puede pagar una cita cancelada");
        }

        if (pagoRepo.existsByCitaId(cita.getId())) {
            throw new IllegalArgumentException("Esta cita ya tiene un pago registrado");
        }

        Pago pago = Pago.builder()
                .cita(cita)
                .monto(dto.monto())
                .metodo(dto.metodo())
                .fechaPago(LocalDateTime.now())
                .build();

        cita.setEstado(Estado.AGENDADA);
        citaRepo.save(cita);

        return pagoRepo.save(pago);
    }
}
