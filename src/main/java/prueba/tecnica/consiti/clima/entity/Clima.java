package prueba.tecnica.consiti.clima.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CLIMA")
public class Clima {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ciudad;
    private String descripcion;
    private double temperatura;
    private double humedad;
    private double velocidadViento;
    private LocalDateTime fechaConsulta;
}
