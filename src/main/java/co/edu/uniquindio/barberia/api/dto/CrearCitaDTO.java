package co.edu.uniquindio.barberia.api.dto;

import java.time.LocalDateTime;

public record CrearCitaDTO(
        Long clienteId,
        Long barberoId,
        Long servicioId,              // 🔗 nuevo campo
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin
) {}
