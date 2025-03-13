package prueba.tecnica.consiti.clima.service;

import prueba.tecnica.consiti.clima.dto.ClimaDTO;
import prueba.tecnica.consiti.clima.dto.ConsultaDTO;
import prueba.tecnica.consiti.clima.dto.ContaminacionDTO;
import prueba.tecnica.consiti.clima.dto.Pronostico5DiasDTo;
import prueba.tecnica.consiti.clima.security.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IConsultaService {
    Optional<ClimaDTO> climaPorCiudad(Usuario usuario, String ciudad);
    Optional<List<Pronostico5DiasDTo>>  pronosticoPorCiudad(Usuario usuario, String ciudad);
    Optional<ContaminacionDTO> contaminacionPorCiudad(Usuario usuario, String ciudad);
    Optional<ConsultaDTO> buscarPorId(int id);
    List<ConsultaDTO>  buscarPorId(String email);
    List<ConsultaDTO> traerTodasConsultas();
}
