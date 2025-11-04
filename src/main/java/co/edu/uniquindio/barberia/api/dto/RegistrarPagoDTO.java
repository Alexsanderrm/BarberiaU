package co.edu.uniquindio.barberia.api.dto;

public record RegistrarPagoDTO(
        Long idCita,
        double monto,
        String metodo
) {}
