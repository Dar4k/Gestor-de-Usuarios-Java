package Controller;

import Model.Usuario;
import Service.UsuarioService;
import java.sql.SQLException;
import java.util.Map;

public class UsuarioController {

    public UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void crearUsuario(int identificacion, String nombre, String correo, String Contraseña, String categoria, boolean estado) throws ClassNotFoundException {
        Usuario usuario = new Usuario(identificacion, nombre, correo, Contraseña, categoria, true);
        usuarioService.agregarUsuario(usuario);
    }

    public Usuario loginUsuario(String correo, String clave) throws SQLException {
        return usuarioService.iniciarSesion(correo, clave);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        UsuarioService service = new UsuarioService();
        try {
            return service.actualizarUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarUsuarioPorId(int identificacion) throws SQLException {
        return usuarioService.buscarUsuarioPorId(identificacion);
    }

    public boolean eliminarUsuario(int identificacion) {
        try {
            return usuarioService.eliminarUsuario(identificacion);
        } catch (Exception e) {
            return false;
        }
    }

    public java.util.List<Usuario> obtenerTodosUsuarios() {
        try {
            return usuarioService.obtenerTodosUsuarios();
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }
    
    public Map<String, Integer> obtenerIndicadoresActividad(int dias) throws SQLException {
        return usuarioService.contarUsuariosPorRol();
    }
    
    public Map<String, Integer> obtenerIniciosPorFecha() throws SQLException {
        return usuarioService.contarIniciosPorFecha();
    }
    
}
