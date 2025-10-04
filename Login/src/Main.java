
import Controller.UsuarioController;
import Service.UsuarioService;

public class Main {

    
    public static void main(String[] args) {
        //Login login = new Login();
        //login.setVisible(true);
        
        UsuarioService usuarioService = new UsuarioService();
        UsuarioController usuarioController = new UsuarioController(usuarioService);
    }
}
