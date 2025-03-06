package prueba.tecnica.consiti.clima.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prueba.tecnica.consiti.clima.dto.UsuarioDTO;
import prueba.tecnica.consiti.clima.security.entity.Usuario;
import prueba.tecnica.consiti.clima.security.repository.IUsuarioRepository;
import prueba.tecnica.consiti.clima.service.IUsuarioService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaDeRegistro(LocalDate.now());
        return usuarioRepository.save(usuario);
    }
    @Override
    public Optional<UsuarioDTO> buscarPorId(int id) {
        return usuarioRepository.findById(id)
                .map(usuario -> new UsuarioDTO(
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getFechaDeRegistro()
                ));
    }
    @Override
    public List<UsuarioDTO> trearTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getFechaDeRegistro()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Usuario> actualizarUsuario(int id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuarioDTO.getNombre() != null && !usuarioDTO.getNombre().isEmpty()) {
                usuario.setNombre(usuarioDTO.getNombre());
            }
            if (usuarioDTO.getApellido() != null && !usuarioDTO.getApellido().isEmpty()) {
                usuario.setApellido(usuarioDTO.getApellido());
            }
            if (usuarioDTO.getEmail() != null && !usuarioDTO.getEmail().isEmpty()) {
                usuario.setEmail(usuarioDTO.getEmail());
            }
            usuarioRepository.save(usuario);
        }
        return usuarioOpt;
    }
    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
