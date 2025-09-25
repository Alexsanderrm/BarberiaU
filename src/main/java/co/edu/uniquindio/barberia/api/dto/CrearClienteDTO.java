package co.edu.uniquindio.barberia.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearClienteDTO(
        @NotBlank String nombre,
        @NotBlank String documento,
        @NotBlank String telefono
) {}