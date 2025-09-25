package co.edu.uniquindio.barberia.api.dto;

import java.time.LocalTime;

public record HorarioDTO(int diaSemana, LocalTime horaInicio, LocalTime horaFin) {}
