package Controller;

import Model.Usuario;
import Service.UsuarioService;

public class UsuarioController {
    public UsuarioService usuarioService;
    
    public UsuarioController() {
        
    }

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    public void crearUsuario(String nombre, String contraseña) throws ClassNotFoundException{
        Usuario usuario = new Usuario(nombre, contraseña);
        usuarioService.agregarUsuario(usuario);
    }
    
    /*public void encontrarUsuario(int id) {
        Usuario usuario = usuarioService.buscarUsuario(id);
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getNombre());
        } else {
            System.out.println("Usuario no encontrado");
        }
    }
    
    public void listaUsaurios(){
        List<Usuario> lista = usuarioService.obtenerUsduarios();
        for (Usuario usuario : lista) {
            System.out.println("id;" + usuario.getNombre() + usuario.getContraseña());
        }
    }*/

}
