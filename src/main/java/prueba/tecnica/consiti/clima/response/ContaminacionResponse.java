package prueba.tecnica.consiti.clima.response;

import lombok.Data;
import java.util.List;

@Data
public class ContaminacionResponse {
    private List<ContaminacionData> list;

    @Data
    public static class ContaminacionData {
        private Main main;
        private Coord coord;
    }

    @Data
    public static class Main {
        private int aqi;
    }

    @Data
    public static class Coord {
        private double lat;
        private double lon;
    }
}
