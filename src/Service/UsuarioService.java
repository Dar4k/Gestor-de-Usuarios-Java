package Service;

import java.sql.Connection;
import BD.Conexion;
import Controller.SecuriteController;
import Model.Usuario;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Model.CambioUsuario;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UsuarioService extends Conexion {

    Connection conn = getConexion();
    private CambioUsuarioService cambioService;
    
     public UsuarioService() {
        // Inicializa el servicio de registro de cambios
        this.cambioService = new CambioUsuarioService(); 
    }


    public boolean agregarUsuario(Usuario usuario,String usuarioSistema) throws ClassNotFoundException {

        String sql = "INSERT INTO Usuario (identificacion, nombre, correo, Contraseña, categoria, Estado) VALUES (?, ?, ?, ?, ?, ?)";
         boolean exito = false; 

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdentificacion());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContraseña().toString());
            ps.setString(5, usuario.getcategoria());
            ps.setBoolean(6, true);
            int filas = ps.executeUpdate();
            exito = filas > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
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

    public boolean actualizarUsuario(Usuario usuario,String usuarioSistema) throws ClassNotFoundException {
        String sql = "UPDATE Usuario SET nombre=?, correo=?, Contraseña=?, categoria=?, Estado=? WHERE identificacion=?";
        boolean exito = false;
        try {
            var ps = conn.prepareStatement(sql);
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getCorreo());
        String hash = SecuriteController.hashClave(usuario.getContraseña());
        ps.setString(3, hash);
        ps.setString(4, usuario.getcategoria());
        ps.setBoolean(5, usuario.isEstado());
        ps.setInt(6, usuario.getIdentificacion());

           int filas = ps.executeUpdate();
            exito = filas > 0;
            
           return exito;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario buscarUsuarioPorId(int identificacion) {
        String sql = "SELECT ID, identificacion, Nombre, correo, Contraseña, categoria, Estado FROM Usuario WHERE identificacion = ?";

        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, identificacion);
            var rs = ps.executeQuery();

           if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("ID")); 
            usuario.setIdentificacion(rs.getInt("identificacion"));
            usuario.setNombre(rs.getString("Nombre"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setContraseña(rs.getString("Contraseña"));
            usuario.setcategoria(rs.getString("categoria"));
            usuario.setEstado(rs.getBoolean("Estado")); 
            return usuario;
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarUsuario(int identificacion,String usuarioSistema) {
        Usuario usuarioEliminado = buscarUsuarioPorId(identificacion); 
        
        String sql = "UPDATE Usuario set Estado = 0 WHERE identificacion = ?";
        boolean exito = false;
        try {
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, identificacion);
            
            int filas = ps.executeUpdate();
            exito = filas > 0;
            
             
            return exito;
        } catch (SQLException e) {
            System.err.println("Error al inactivar usuario: " + e.getMessage());
            return false;
        }
    }

    public java.util.List<Usuario> obtenerTodosUsuarios() {
        String sql = "SELECT ID, identificacion, Nombre, correo, Contraseña, categoria, Estado FROM Usuario";
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
                usuario.setEstado(rs.getBoolean("Estado"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    public Map<String, Integer> contarUsuariosPorRol() throws SQLException {
    String sql = "SELECT Categoria, COUNT(*) AS total FROM usuario GROUP BY Categoria";
    Map<String, Integer> resultado = new HashMap<>();
    
        try (var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("Categoria"), rs.getInt("total"));
            }
        }
        return resultado;
    }
    
    public Map<String, Integer> contarIniciosPorFecha() throws SQLException {
    String sql = """
        SELECT DATE(ultima_sesion) AS fecha, COUNT(*) AS total
        FROM Usuario
        WHERE ultima_sesion IS NOT NULL
        GROUP BY DATE(ultima_sesion)
        ORDER BY fecha ASC
    """;

    Map<String, Integer> resultado = new LinkedHashMap<>();

        try (var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("fecha"), rs.getInt("total"));
            }
        }
        return resultado;
    }
}
    