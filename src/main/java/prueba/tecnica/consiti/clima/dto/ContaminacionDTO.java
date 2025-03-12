package prueba.tecnica.consiti.clima.dto;

import lombok.Data;

@Data
public class ContaminacionDTO {
    private String ciudad;
    private double latitud;
    private double longitud;
    private int indiceContaminacion;
}
