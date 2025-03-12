package prueba.tecnica.consiti.clima.dto;

import lombok.Data;

@Data
public class Pronostico5DiasDTo {
    private String ciudad;
    private String fecha;
    private double temperatura;
    private int humedad;
    private String descripcion;
    private double velocidadViento;
    private double latitud;
    private double longitud;
}
