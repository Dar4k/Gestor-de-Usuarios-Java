package Service;

import BD.Conexion;
import Model.CambioUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CambioUsuarioService extends Conexion {

    Connection conn = getConexion();

    public void registrarCambio(CambioUsuario cambio) {
        String sql = "INSERT INTO historial_cambios (fecha_hora, accion, usuario_sistema, detalle) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(cambio.getFechaHora().getTime()));
            ps.setString(2, cambio.getAccion());
            ps.setString(3, cambio.getUsuarioSistema());
            ps.setString(4, cambio.getDetalle());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error al registrar cambio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CambioUsuario> listarCambios() {
        List<CambioUsuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_cambios ORDER BY fecha_hora DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CambioUsuario cambio = new CambioUsuario();
                cambio.setId(rs.getInt("id"));
                cambio.setFechaHora(rs.getTimestamp("fecha_hora"));
                cambio.setAccion(rs.getString("accion"));
                    cambio.setUsuarioSistema(rs.getString("usuario_sistema"));
                cambio.setDetalle(rs.getString("detalle"));
                lista.add(cambio);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Error al listar cambios: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public List<CambioUsuario> listarCambiosPorUsuario(String usuarioSistema) {
        List<CambioUsuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_cambios WHERE usuario_sistema = ? ORDER BY fecha_hora DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuarioSistema);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CambioUsuario cambio = new CambioUsuario();
                cambio.setId(rs.getInt("id"));
                cambio.setFechaHora(rs.getTimestamp("fecha_hora"));
                cambio.setAccion(rs.getString("accion"));
                cambio.setUsuarioSistema(rs.getString("usuario_sistema"));
                cambio.setDetalle(rs.getString("detalle"));
                lista.add(cambio);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Error al listar cambios por usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public List<CambioUsuario> listarCambiosPorAccion(String accion) {
        List<CambioUsuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_cambios WHERE accion = ? ORDER BY fecha_hora DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accion);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CambioUsuario cambio = new CambioUsuario();
                cambio.setId(rs.getInt("id"));
                cambio.setFechaHora(rs.getTimestamp("fecha_hora"));
                cambio.setAccion(rs.getString("accion"));
                cambio.setUsuarioSistema(rs.getString("usuario_sistema"));
                cambio.setDetalle(rs.getString("detalle"));
                lista.add(cambio);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Error al listar cambios por acción: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}