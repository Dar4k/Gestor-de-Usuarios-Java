package Model;

public class Usuario {
    public int ID;
    public int identificacion;
    public String nombre;
    public String correo;
    public String contraseña;
    public String Categoria;
    public boolean Estado;

    public Usuario() {
    }

    public Usuario( int identificacion, String nombre, String correo, String contraseña, String Categoria, boolean estado) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.Categoria = Categoria;
        this.Estado = estado;
    }

    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getcategoria() {
        return Categoria;
    }

    public void setcategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean Estado) {
        this.Estado = Estado;
    }
    
    
    public String getEstadoTexto(){
        return Estado ? "Activo" : "Inactivo";
    }
    
}
