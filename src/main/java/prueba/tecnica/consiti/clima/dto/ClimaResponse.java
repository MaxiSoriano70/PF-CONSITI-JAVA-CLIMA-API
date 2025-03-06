package prueba.tecnica.consiti.clima.dto;
import lombok.Data;

@Data
public class ClimaResponse {
    private Main main;
    private Wind wind;
    private Weather[] weather;
    private String name;

    @Data
    public static class Main {
        private double temp;
        private double humidity;
    }

    @Data
    public static class Wind {
        private double speed;
    }

    @Data
    public static class Weather {
        private String description;
    }
}
