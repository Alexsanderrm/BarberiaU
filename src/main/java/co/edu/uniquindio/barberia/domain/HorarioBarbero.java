package co.edu.uniquindio.barberia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HorarioBarbero {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Barbero barbero;

    /** 1=Lunes ... 7=Domingo */
    private int diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFin;
}