package prueba.tecnica.consiti.clima.dto;

import lombok.Data;

@Data
public class ClimaDTO {
    private String ciudad;
    private Double temperatura;
    private Integer humedad;
    private String descripcion;
    private Double velocidadViento;
    private Double latitud;
    private Double longitud;
}

