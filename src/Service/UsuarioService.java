package Service;

import java.sql.Connection;
import BD.Conexion;
import Controller.SecuriteController;
import Model.Usuario;
import java.sql.SQLException;

public class UsuarioService extends Conexion {

    Connection conn = getConexion();

    public boolean agregarUsuario(Usuario usuario) throws ClassNotFoundException {

        String sql = "INSERT INTO Usuario (identificacion, nombre, correo, Contraseña, categoria, Estado) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdentificacion());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContraseña().toString());
            ps.setString(5, usuario.getcategoria());
            ps.setBoolean(6, true);
            ps.execute();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public Usuario iniciarSesion(String correo, String clave) throws SQLException {
        String sql = "SELECT identificacion, Nombre, correo, Contraseña, categoria, Estado FROM Usuario WHERE correo = ?";
        try {
            var ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            var rs = ps.executeQuery();
            if (rs.next()) {
                String claveGuardada = rs.getString("Contraseña");
                String claveIngresada = SecuriteController.hashClave(clave);

                if (claveGuardada.equals(claveIngresada)) {
                    int id = rs.getInt("identificacion");

                    String updateSql = "UPDATE Usuario SET ultima_sesion = NOW() WHERE identificacion = ?";
                    try (var psUpdate = conn.prepareStatement(updateSql)) {
                        psUpdate.setInt(1, id);
                        psUpdate.executeUpdate();
                    }
                    String usuario = rs.getString("Nombre");
                    String email = rs.getString("correo");
                    String categoria = rs.getString("categoria");
                    boolean estado = rs.getBoolean("Estado");

                    return new Usuario(id, usuario, email, claveGuardada, categoria, estado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarUsuario(Usuario usuario) throws ClassNotFoundException {
        String sql = "UPDATE Usuario SET nombre=?, correo=?, Contraseña=?, categoria=? WHERE identificacion=?";
        try {
            var ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            String hash = SecuriteController.hashClave(usuario.getContraseña());
            ps.setString(3, hash);
            ps.setString(4, usuario.getcategoria());
            ps.setInt(5, usuario.getIdentificacion());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarUsuarioPorId(int identificacion) {
        String sql = "SELECT ID, identificacion, Nombre, correo, Contraseña, categoria FROM Usuario WHERE identificacion = ?";

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, identificacion);
            var rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdentificacion(rs.getInt("identificacion"));
                usuario.setNombre(rs.getString("Nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("Contraseña"));
                usuario.setcategoria(rs.getString("categoria"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarUsuario(int identificacion) {
        String sql = "UPDATE Usuario set Estado = 0 WHERE identificacion = ?";
        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, identificacion);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public java.util.List<Usuario> obtenerTodosUsuarios() {
        String sql = "SELECT ID, identificacion, Nombre, correo, Contraseña, categoria FROM Usuario";
        java.util.List<Usuario> usuarios = new java.util.ArrayList<>();

        try {
            var ps = conn.prepareStatement(sql);
            var rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setIdentificacion(rs.getInt("identificacion"));
                usuario.setNombre(rs.getString("Nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("Contraseña"));
                usuario.setcategoria(rs.getString("categoria"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    
    /*public Usuario buscarUsuario(int id) {
        
    }*/

 /* public List<Usuario> obtenerUsduarios(){
        
    }*/
}
