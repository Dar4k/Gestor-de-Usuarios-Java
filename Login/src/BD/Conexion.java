package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/proyectologin";
    private static final String USER = "LOKI";
    private static final String PASSWORD = "123";
    

    public static Connection getConexion(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa");
        } catch (SQLException  e){
            System.out.println("error de conexion");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) { 
            System.out.println("No se encontro el driver");
            ex.printStackTrace();
        }
        return conn;
    }
}