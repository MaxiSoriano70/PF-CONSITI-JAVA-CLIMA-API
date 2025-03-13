package prueba.tecnica.consiti.clima.dto;

import lombok.Data;
import prueba.tecnica.consiti.clima.utils.ETipoConsulta;

import java.time.LocalDateTime;
@Data
public class ConsultaDTO {
    private String usuario;
    private String ciudad;
    private ETipoConsulta tipoConsulta;
    private LocalDateTime fechaHoraConsulta;
}
