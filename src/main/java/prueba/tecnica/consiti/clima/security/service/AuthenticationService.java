package prueba.tecnica.consiti.clima.security.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prueba.tecnica.consiti.clima.security.dto.AuthenticationRequest;
import prueba.tecnica.consiti.clima.security.dto.AuthenticationResponse;
import prueba.tecnica.consiti.clima.security.dto.RegisterRequest;
import prueba.tecnica.consiti.clima.security.entity.Usuario;
import prueba.tecnica.consiti.clima.security.repository.IUsuarioRepository;
import prueba.tecnica.consiti.clima.security.utils.ERol;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final IUsuarioRepository usuarioRepository;
    private final JtwService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        ERol role = request.getRole() != null ? request.getRole() : ERol.USER;

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .fechaDeRegistro(LocalDate.now())
                .cantidadConsultas(500)
                .fechaRestauracionConsultas(LocalDate.now())
                .build();

        usuarioRepository.save(usuario);
        String jwtToken = jwtService.generateToken(usuario);
        return AuthenticationResponse.builder()
                .jwt(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String jwtToken = jwtService.generateToken(usuario);
        return AuthenticationResponse.builder()
                .jwt(jwtToken)
                .build();
    }
}
