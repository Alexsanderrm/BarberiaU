package co.edu.uniquindio.barberia.api.dto;

import java.time.LocalDateTime;

public record CrearCitaDTO(
        Long clienteId,
        Long barberoId,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin
) {}
