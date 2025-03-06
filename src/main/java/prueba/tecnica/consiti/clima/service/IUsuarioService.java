package prueba.tecnica.consiti.clima.service;

import prueba.tecnica.consiti.clima.dto.UsuarioDTO;
import prueba.tecnica.consiti.clima.security.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Usuario crearUsuario (Usuario usuario);
    Optional<UsuarioDTO> buscarPorId(int id);
    List<UsuarioDTO> trearTodosUsuarios();
    Optional<Usuario> actualizarUsuario(int id, UsuarioDTO usuarioDTO);
    void eliminarUsuario(Integer id);
}
