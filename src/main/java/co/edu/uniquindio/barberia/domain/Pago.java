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
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cita cita;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private String metodo;

    @Column(nullable = false)
    private LocalDateTime fechaPago;
}
