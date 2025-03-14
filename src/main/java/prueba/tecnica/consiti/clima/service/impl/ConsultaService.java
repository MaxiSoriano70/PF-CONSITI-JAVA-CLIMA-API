package prueba.tecnica.consiti.clima.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import prueba.tecnica.consiti.clima.dto.ClimaDTO;
import prueba.tecnica.consiti.clima.dto.ConsultaDTO;
import prueba.tecnica.consiti.clima.dto.ContaminacionDTO;
import prueba.tecnica.consiti.clima.dto.Pronostico5DiasDTo;
import prueba.tecnica.consiti.clima.entity.Consulta;
import prueba.tecnica.consiti.clima.repository.IConsultaRepository;
import prueba.tecnica.consiti.clima.response.ClimaResponse;
import prueba.tecnica.consiti.clima.response.PronosticoResponse;
import prueba.tecnica.consiti.clima.response.ContaminacionResponse;
import prueba.tecnica.consiti.clima.security.entity.Usuario;
import prueba.tecnica.consiti.clima.security.repository.IUsuarioRepository;
import prueba.tecnica.consiti.clima.security.service.JtwService;
import prueba.tecnica.consiti.clima.service.IConsultaService;
import prueba.tecnica.consiti.clima.utils.ETipoConsulta;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService implements IConsultaService {
    private static final String API_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&lang=es";
    private static final String API_FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&units=metric&lang=es";
    private static final String API_AIR_POLLUTION_URL = "https://api.openweathermap.org/data/2.5/air_pollution?lat={lat}&lon={lon}&appid={apiKey}";
    private final RestTemplate restTemplate;
    private final IUsuarioRepository usuarioRepository;
    private final IConsultaRepository consultaRepository;
    private final JtwService jwtService;
    /*private final String apiKey = System.getenv("OPENWEATHER_API_KEY");*/
    private final String apiKey = "0f5fe8fc0e2d930e421a62c3e452c9f4";

    public ConsultaService(RestTemplate restTemplate, IUsuarioRepository usuarioRepository,
                           IConsultaRepository consultaRepository, JtwService jwtService) {
        this.restTemplate = restTemplate;
        this.usuarioRepository = usuarioRepository;
        this.consultaRepository = consultaRepository;
        this.jwtService = jwtService;
    }
    public boolean registrarConsulta(Usuario usuario, String ciudad, ETipoConsulta tipoConsulta) {
        if (usuario.getFechaRestauracionConsultas().isBefore(LocalDate.now())) {
            usuario.setCantidadConsultas(100);
            usuario.setFechaRestauracionConsultas(LocalDate.now());
        }

        if (usuario.getCantidadConsultas() <= 0) {
            return false;
        }

        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setCiudad(ciudad);
        consulta.setTipoConsulta(tipoConsulta);
        consulta.setFechaHoraConsulta(LocalDateTime.now());
        consultaRepository.save(consulta);

        usuario.setCantidadConsultas(usuario.getCantidadConsultas() - 1);
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    @Cacheable(value = "clima", key = "#ciudad", unless = "#result == null")
    public Optional<ClimaDTO> climaPorCiudad(Usuario usuario, String ciudad) {
        ClimaResponse apiResponse = restTemplate.getForObject(API_WEATHER_URL, ClimaResponse.class, ciudad, apiKey);

        if (apiResponse == null || apiResponse.getMain() == null || apiResponse.getWeather() == null
                || apiResponse.getWind() == null || apiResponse.getCoord() == null
                || apiResponse.getWeather().isEmpty()) {
            return Optional.empty();
        }

        ClimaDTO response = new ClimaDTO();
        response.setCiudad(apiResponse.getCiudad());
        response.setTemperatura(apiResponse.getMain().getTemperatura());
        response.setHumedad(apiResponse.getMain().getHumedad());
        response.setDescripcion(apiResponse.getWeather().get(0).getDescripcion());
        response.setVelocidadViento(apiResponse.getWind().getVelocidadViento());
        response.setLatitud(apiResponse.getCoord().getLatitud());
        response.setLongitud(apiResponse.getCoord().getLongitud());

        if (response.getCiudad() == null || response.getDescripcion() == null
                || response.getTemperatura() == null || response.getHumedad() == null
                || response.getVelocidadViento() == null || response.getLatitud() == null
                || response.getLongitud() == null) {
            return Optional.empty();
        }

        registrarConsulta(usuario, response.getCiudad(), ETipoConsulta.CLIMA);

        return Optional.of(response);
    }

    @Override
    @Cacheable(value = "pronostico", key = "#ciudad", unless = "#result == null")
    public Optional<List<Pronostico5DiasDTo>> pronosticoPorCiudad(Usuario usuario, String ciudad) {
        try {
            String ciudadCodificada = URLEncoder.encode(ciudad, StandardCharsets.UTF_8.toString());
            String url = API_FORECAST_URL.replace("{city}", ciudadCodificada).replace("{apiKey}", apiKey);
            System.out.println("Consultando pronóstico en: " + url);

            ResponseEntity<PronosticoResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, PronosticoResponse.class);

            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("Error al consultar la API, código de estado HTTP: " + responseEntity.getStatusCode());
                return Optional.empty();
            }

            PronosticoResponse response = responseEntity.getBody();
            System.out.println("Respuesta cruda de la API: " + response);

            if (response == null || response.getList() == null || response.getList().isEmpty() || response.getCity() == null || response.getCity().getCoord() == null) {
                System.out.println("No se encontró pronóstico para " + ciudad);
                return Optional.empty();
            }

            List<Pronostico5DiasDTo> pronosticos5DiasDTO = new ArrayList<>();
            String fechaAnterior = "";

            for (PronosticoResponse.Pronostico pronostico : response.getList()) {
                String fecha = pronostico.getDt_txt().substring(0, 10);

                if (!fecha.equals(fechaAnterior)) {
                    Pronostico5DiasDTo dto = new Pronostico5DiasDTo();
                    dto.setCiudad(response.getCity().getName());
                    dto.setFecha(fecha);
                    dto.setTemperatura(pronostico.getMain().getTemp());
                    dto.setHumedad(pronostico.getMain().getHumidity());
                    dto.setDescripcion(pronostico.getWeather().get(0).getDescription());
                    dto.setVelocidadViento(pronostico.getWind().getSpeed());
                    dto.setLatitud(response.getCity().getCoord().getLat());
                    dto.setLongitud(response.getCity().getCoord().getLon());

                    if (dto.getCiudad() == null || dto.getFecha() == null || dto.getDescripcion() == null ||
                            dto.getTemperatura() == null || dto.getHumedad() == null || dto.getVelocidadViento() == null ||
                            dto.getLatitud() == null || dto.getLongitud() == null) {
                        return Optional.empty();
                    }

                    pronosticos5DiasDTO.add(dto);
                    fechaAnterior = fecha;
                }

                if (pronosticos5DiasDTO.size() >= 5) {
                    break;
                }
            }

            if (pronosticos5DiasDTO.isEmpty()) {
                System.out.println("No se encontraron pronósticos para los próximos 5 días en " + ciudad);
                return Optional.empty();
            }

            registrarConsulta(usuario, ciudad, ETipoConsulta.PRONOSTICO);

            return Optional.of(pronosticos5DiasDTO);
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
    @Override
    @Cacheable(value = "contaminacion", key = "#ciudad", unless = "#result == null")
    public Optional<ContaminacionDTO> contaminacionPorCiudad(Usuario usuario, String ciudad) {
        ClimaResponse climaResponse = restTemplate.getForObject(API_WEATHER_URL, ClimaResponse.class, ciudad, apiKey);

        if (climaResponse == null || climaResponse.getCoord() == null) {
            return Optional.empty();
        }

        double lat = climaResponse.getCoord().getLatitud();
        double lon = climaResponse.getCoord().getLongitud();

        String url = API_AIR_POLLUTION_URL.replace("{lat}", String.valueOf(lat))
                .replace("{lon}", String.valueOf(lon))
                .replace("{apiKey}", apiKey);

        System.out.println("Consultando contaminación en: " + url);

        ContaminacionResponse response = restTemplate.getForObject(url, ContaminacionResponse.class);

        if (response == null || response.getList().isEmpty()) {
            return Optional.empty();
        }

        ContaminacionResponse.ContaminacionData data = response.getList().get(0);

        ContaminacionDTO dto = new ContaminacionDTO();
        dto.setCiudad(ciudad);
        dto.setLatitud(lat);
        dto.setLongitud(lon);
        dto.setIndiceContaminacion(data.getMain().getAqi());

        if (dto.getCiudad() == null || dto.getLatitud() == null || dto.getLongitud() == null || dto.getIndiceContaminacion() == null) {
            return Optional.empty();
        }

        registrarConsulta(usuario, ciudad, ETipoConsulta.CONTAMINACION);

        return Optional.of(dto);
    }
    @Override
    public Optional<ConsultaDTO> buscarPorId(int id) {
        return consultaRepository.findById(id)
                .map(consulta -> {
                    ConsultaDTO dto = new ConsultaDTO();
                    dto.setUsuario(consulta.getUsuario().getNombre() + " " + consulta.getUsuario().getApellido());
                    dto.setCiudad(consulta.getCiudad());
                    dto.setTipoConsulta(consulta.getTipoConsulta());
                    dto.setFechaHoraConsulta(consulta.getFechaHoraConsulta());
                    return dto;
                });
    }

    @Override
    public List<ConsultaDTO> buscarPorId(String email) {
        return consultaRepository.findByUsuarioEmail(email).stream()
                .map(consulta -> {
                    ConsultaDTO dto = new ConsultaDTO();
                    dto.setUsuario(consulta.getUsuario().getNombre() + " " + consulta.getUsuario().getApellido());
                    dto.setCiudad(consulta.getCiudad());
                    dto.setTipoConsulta(consulta.getTipoConsulta());
                    dto.setFechaHoraConsulta(consulta.getFechaHoraConsulta());
                    return dto;
                })
                .toList();
    }
    @Override
    public List<ConsultaDTO> traerTodasConsultas() {
        return consultaRepository.findAll().stream()
                .map(consulta -> {
                    ConsultaDTO dto = new ConsultaDTO();
                    dto.setUsuario(consulta.getUsuario().getNombre() + " " + consulta.getUsuario().getApellido());
                    dto.setCiudad(consulta.getCiudad());
                    dto.setTipoConsulta(consulta.getTipoConsulta());
                    dto.setFechaHoraConsulta(consulta.getFechaHoraConsulta());
                    return dto;
                })
                .toList();
    }
}
