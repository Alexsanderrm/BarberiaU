package co.edu.uniquindio.barberia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ActualizarServicioDTO(
        @NotNull Long id,
        String nombre,
        @Min(0) double precio
) {}
