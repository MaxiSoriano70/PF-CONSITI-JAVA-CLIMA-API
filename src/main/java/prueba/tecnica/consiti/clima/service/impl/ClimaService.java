package prueba.tecnica.consiti.clima.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import prueba.tecnica.consiti.clima.dto.ClimaResponse;
import prueba.tecnica.consiti.clima.entity.Clima;
import prueba.tecnica.consiti.clima.repository.IClimaRepository;
import prueba.tecnica.consiti.clima.service.IClimaService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClimaService implements IClimaService {
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&lang=es";

    private final RestClient restClient;
    private final IClimaRepository climaRepository;
    private final String apiKey = "0f5fe8fc0e2d930e421a62c3e452c9f4";

    public ClimaService(RestClient restClient, IClimaRepository climaRepository) {
        this.restClient = restClient;
        this.climaRepository = climaRepository;
    }

    @Override
    @Cacheable(value = "clima", key = "#ciudad", unless = "#result == null")
    public Optional<Clima> climaPorCiudad(String ciudad) {
        // Buscar en la base de datos la consulta más reciente
        Optional<Clima> climaEnDB = climaRepository.findByCiudad(ciudad); // Cambié el nombre del método
        if (climaEnDB.isPresent() && climaEnDB.get().getFechaConsulta().isAfter(LocalDateTime.now().minusMinutes(30))) {
            return climaEnDB;
        }

        // Llamar a OpenWeatherMap con RestClient
        ClimaResponse response = restClient.get()
                .uri(API_URL, ciudad, apiKey)
                .retrieve()
                .body(ClimaResponse.class);

        if (response == null) return Optional.empty();

        // Mapear la respuesta a la entidad Clima
        Clima clima = Clima.builder()
                .ciudad(response.getName())
                .descripcion(response.getWeather()[0].getDescription())
                .temperatura(response.getMain().getTemp())
                .humedad(response.getMain().getHumidity())
                .velocidadViento(response.getWind().getSpeed())
                .fechaConsulta(LocalDateTime.now())
                .build();

        // Guardar en la base de datos
        climaRepository.save(clima);
        return Optional.of(clima);
    }

}
