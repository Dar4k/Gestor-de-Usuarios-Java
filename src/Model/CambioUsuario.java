package Model;

import java.util.Date;

public class CambioUsuario {
    private int id;
    private Date fechaHora;
    private String accion;
    private String usuarioSistema;
    private String detalle;
    

    public CambioUsuario() {}

    public CambioUsuario(Date fechaHora, String accion, String usuarioSistema, String detalle) {
        this.fechaHora = fechaHora;
        this.accion = accion;
        this.usuarioSistema = usuarioSistema;
        this.detalle = detalle;
    }

  
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getUsuarioSistema() { return usuarioSistema; }
    public void setUsuarioSistema(String usuarioSistema) { this.usuarioSistema = usuarioSistema; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
