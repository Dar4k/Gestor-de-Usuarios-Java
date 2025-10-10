package Controller;

import Model.Usuario;
import Model.CambioUsuario;
import Service.UsuarioService;
import Service.CambioUsuarioService;
import View.Login;
import java.sql.SQLException;
import java.util.Map;

public class UsuarioController {

    private UsuarioService usuarioService;
    private CambioUsuarioService cambioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
        this.cambioService = new CambioUsuarioService();
    }

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.cambioService = new CambioUsuarioService();
    }

   
   public void crearUsuario(int identificacion, String nombre, String correo, String Contraseña, String categoria, boolean estado) throws ClassNotFoundException {
        Usuario usuario = new Usuario(identificacion, nombre, correo, Contraseña, categoria, estado);
        
        
        String usuarioSistema = (Login.usuarioActivo != null) ? Login.usuarioActivo.getNombre() : "Desconocido";
        
        usuarioService.agregarUsuario(usuario, usuarioSistema);
        registrarCambio("CREAR", "Usuario creado: " + nombre);
    }

    
    public Usuario loginUsuario(String correo, String clave) throws SQLException {
        return usuarioService.iniciarSesion(correo, clave);
    }


    public boolean actualizarUsuario(Usuario usuario) {
        try {
           
            String usuarioSistema = (Login.usuarioActivo != null) ? Login.usuarioActivo.getNombre() : "Desconocido";
            
            boolean actualizado = usuarioService.actualizarUsuario(usuario, usuarioSistema);
            if (actualizado) {
                registrarCambio("ACTUALIZAR", "Usuario actualizado: " + usuario.getNombre());
            }
            return actualizado;
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
            
            String usuarioSistema = (Login.usuarioActivo != null) ? Login.usuarioActivo.getNombre() : "Desconocido";
            
            boolean eliminado = usuarioService.eliminarUsuario(identificacion, usuarioSistema);
            if (eliminado) {
                registrarCambio("ELIMINAR", "Usuario eliminado con ID: " + identificacion);
            }
            return eliminado;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Usuario> obtenerTodosUsuarios() {
        try {
            return usuarioService.obtenerTodosUsuarios();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    
    public Map<String, Integer> obtenerIndicadoresActividad(int dias) throws SQLException {
        return usuarioService.contarUsuariosPorRol();
    }
    
    public Map<String, Integer> obtenerIniciosPorFecha() throws SQLException {
        return usuarioService.contarIniciosPorFecha();
    }
    
}
