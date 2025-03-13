package prueba.tecnica.consiti.clima.dto;

import lombok.Data;

@Data
public class ContaminacionDTO {
    private String ciudad;
    private Double latitud;
    private Double longitud;
    private Integer indiceContaminacion;
}
