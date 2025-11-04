package co.edu.uniquindio.barberia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(columnList = "barbero_id, fechaHoraInicio"),
        @Index(columnList = "cliente_id, fechaHoraInicio")
})
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Barbero barbero;

    @ManyToOne(optional = false)
    private Servicio servicio;   // 🔗 Nuevo campo: conecta la cita con el servicio solicitado

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado {
        AGENDADA,
        CANCELADA,
        COMPLETADA
    }
}
