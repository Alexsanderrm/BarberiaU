package co.edu.uniquindio.barberia.api.dto;

import java.util.List;

public record ActualizarDisponibilidadDTO(
        Long idBarbero,
        List<DiaHorarioDTO> horariosDisponibles
) {}

