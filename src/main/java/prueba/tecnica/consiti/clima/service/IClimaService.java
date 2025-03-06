package prueba.tecnica.consiti.clima.service;

import prueba.tecnica.consiti.clima.entity.Clima;

import java.util.Optional;

public interface IClimaService {
    Optional<Clima> climaPorCiudad(String ciudad);
}
