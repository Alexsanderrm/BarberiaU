package co.edu.uniquindio.barberia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CrearServicioDTO(
        @NotBlank String nombre,
        @Min(0) double precio
) {}
