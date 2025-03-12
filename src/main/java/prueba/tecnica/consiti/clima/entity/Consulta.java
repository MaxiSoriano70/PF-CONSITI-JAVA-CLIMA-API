package prueba.tecnica.consiti.clima.entity;

import jakarta.persistence.*;
import lombok.*;
import prueba.tecnica.consiti.clima.security.entity.Usuario;
import prueba.tecnica.consiti.clima.utils.ETipoConsulta;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CONSULTA")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String ciudad;

    @Enumerated(EnumType.STRING)
    private ETipoConsulta tipoConsulta;

    @Column(nullable = false)
    private LocalDateTime fechaHoraConsulta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
