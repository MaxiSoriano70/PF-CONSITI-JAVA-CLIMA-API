package prueba.tecnica.consiti.clima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prueba.tecnica.consiti.clima.entity.Clima;
import prueba.tecnica.consiti.clima.service.IClimaService;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {
    @Autowired
    private IClimaService climaService;

    @GetMapping("/{ciudad}")
    public ResponseEntity<Clima> obtenerClima(@PathVariable String ciudad) {
        return climaService.climaPorCiudad(ciudad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
