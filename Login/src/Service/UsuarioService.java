package Service;

import java.sql.Connection;
import BD.Conexion;
import Model.Usuario;
import java.sql.SQLException;


public class UsuarioService extends Conexion {
    
    public boolean agregarUsuario(Usuario usuario) throws ClassNotFoundException {
        
        Connection conn = getConexion();
        
        String sql = "INSERT INTO Usuario (nombre, contraseña) VALUES (?, ?)";
        
        try{
            var ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getContraseña());
            ps.execute();
            
            return true;
            
        } catch(SQLException e){
            return false;
        }
    }
    
    /*public Usuario buscarUsuario(int id) {
        
    }*/
    
   /* public List<Usuario> obtenerUsduarios(){
        
    }*/
}
