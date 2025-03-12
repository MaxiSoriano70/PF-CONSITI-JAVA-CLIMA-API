package prueba.tecnica.consiti.clima.service;

import prueba.tecnica.consiti.clima.dto.ClimaDTO;
import prueba.tecnica.consiti.clima.dto.ContaminacionDTO;
import prueba.tecnica.consiti.clima.dto.Pronostico5DiasDTo;
import prueba.tecnica.consiti.clima.response.ContaminacionResponse;

import java.util.List;
import java.util.Optional;

public interface IConsultaService {
    Optional<ClimaDTO> climaPorCiudad(String ciudad);
    Optional<List<Pronostico5DiasDTo>>  pronosticoPorCiudad(String ciudad);
    Optional<ContaminacionDTO> contaminacionPorCiudad(String ciudad);
}
