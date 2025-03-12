package prueba.tecnica.consiti.clima.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClimaResponse {
    @JsonProperty("name")
    private String ciudad;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("coord")
    private Coord coord;

    @Data
    public static class Main {
        @JsonProperty("temp")
        private double temperatura;

        @JsonProperty("humidity")
        private int humedad;
    }

    @Data
    public static class Weather {
        @JsonProperty("description")
        private String descripcion;
    }

    @Data
    public static class Wind {
        @JsonProperty("speed")
        private double velocidadViento;
    }

    @Data
    public static class Coord {
        @JsonProperty("lat")
        private double latitud;

        @JsonProperty("lon")
        private double longitud;
    }
}
