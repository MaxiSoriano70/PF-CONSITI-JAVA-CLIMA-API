package prueba.tecnica.consiti.clima.dto;

import lombok.Data;

@Data
public class ClimaDTO {
    private String ciudad;
    private double temperatura;
    private int humedad;
    private String descripcion;
    private double velocidadViento;
    private double latitud;
    private double longitud;
}
