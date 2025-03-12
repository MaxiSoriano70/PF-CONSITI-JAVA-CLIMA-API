package prueba.tecnica.consiti.clima.response;

import lombok.Data;
import java.util.List;

@Data
public class PronosticoResponse {
    private City city;
    private List<Pronostico> list;

    @Data
    public static class City {
        private String name;
        private String country;
        private Coord coord;
    }

    @Data
    public static class Coord {
        private double lat;
        private double lon;
    }

    @Data
    public static class Pronostico {
        private String dt_txt;
        private Main main;
        private List<Weather> weather;
        private Wind wind;
    }

    @Data
    public static class Main {
        private double temp;
        private int humidity;
    }

    @Data
    public static class Weather {
        private String description;
    }

    @Data
    public static class Wind {
        private double speed;
    }
}
