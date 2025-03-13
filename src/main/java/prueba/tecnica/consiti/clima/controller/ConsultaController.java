package prueba.tecnica.consiti.clima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prueba.tecnica.consiti.clima.dto.ClimaDTO;
import prueba.tecnica.consiti.clima.dto.ConsultaDTO;
import prueba.tecnica.consiti.clima.dto.ContaminacionDTO;
import prueba.tecnica.consiti.clima.dto.Pronostico5DiasDTo;
import prueba.tecnica.consiti.clima.security.entity.Usuario;
import prueba.tecnica.consiti.clima.security.repository.IUsuarioRepository;
import prueba.tecnica.consiti.clima.service.IConsultaService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ConsultaController {
    @Autowired
    private IConsultaService consultaService;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @GetMapping("clima/{ciudad}")
    public ResponseEntity<ClimaDTO> obtenerClimaPorCiudad(@PathVariable String ciudad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        System.out.println("Usuario autenticado: " + usuario.getEmail());

        return consultaService.climaPorCiudad(usuario, ciudad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("pronostico/{ciudad}")
    public ResponseEntity<List<Pronostico5DiasDTo>> obtenerPronosticoPorCiudad(@PathVariable String ciudad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        System.out.println("Usuario autenticado: " + usuario.getEmail());

        return consultaService.pronosticoPorCiudad(usuario, ciudad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("contaminacion/{ciudad}")
    public ResponseEntity<ContaminacionDTO> obtenerContaminacionPorCiudad(@PathVariable String ciudad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        System.out.println("Usuario autenticado: " + usuario.getEmail());

        return consultaService.contaminacionPorCiudad(usuario, ciudad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("auditoria")
    public ResponseEntity<List<ConsultaDTO>> traerTodasLasConsultas() {
        return ResponseEntity.ok(consultaService.traerTodasConsultas());
    }
    @GetMapping("auditoria/id/{id}")
    public ResponseEntity<ConsultaDTO> traerConsultaPorId(@PathVariable int id) {
        return consultaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("auditoria/email/{email}")
    public ResponseEntity<List<ConsultaDTO>> obtenerConsultasPorEmail(@PathVariable String email) {
        List<ConsultaDTO> consultas = consultaService.buscarPorId(email);

        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(consultas);
    }
}
