package prueba.tecnica.consiti.clima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prueba.tecnica.consiti.clima.dto.ClimaDTO;
import prueba.tecnica.consiti.clima.dto.ContaminacionDTO;
import prueba.tecnica.consiti.clima.dto.Pronostico5DiasDTo;
import prueba.tecnica.consiti.clima.response.ContaminacionResponse;
import prueba.tecnica.consiti.clima.service.IConsultaService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ConsultaController {
    @Autowired
    private IConsultaService consultaService;

    @GetMapping("clima/{ciudad}")
    public ResponseEntity<ClimaDTO> obtenerClimaPorCiudad(@PathVariable String ciudad) {
        return consultaService.climaPorCiudad(ciudad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("pronostico/{ciudad}")
    public ResponseEntity<List<Pronostico5DiasDTo>> obtenerPronosticoPorCiudad(@PathVariable String ciudad) {
        return consultaService.pronosticoPorCiudad(ciudad)
                .map(pronosticos -> ResponseEntity.ok(pronosticos))  // Aquí se pasa la lista de DTOs
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("contaminacion/{ciudad}")
    public ResponseEntity<ContaminacionDTO> obtenerContaminacionPorCiudad(@PathVariable String ciudad) {
        return consultaService.contaminacionPorCiudad(ciudad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
