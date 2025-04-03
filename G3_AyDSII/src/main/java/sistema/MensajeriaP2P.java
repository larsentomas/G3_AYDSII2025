package sistema;
import controladores.Controlador;
import controladores.LoginController;
import vistas.Login;
import vistas.VistaInicio;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MensajeriaP2P {
    private String ip_client;
    private Usuario user;
    private static MensajeriaP2P instance = null;

    public static void main(String[] args) throws UnknownHostException {
        try {
            instance = MensajeriaP2P.getInstance();
            InetAddress localHost = InetAddress.getLocalHost();
            instance.ip_client = localHost.getHostAddress();

            VistaInicio vista_inicio = new VistaInicio();
            Login login = new Login();
            LoginController controlador_login = new LoginController(login, vista_inicio);
            Controlador controlador = new Controlador(vista_inicio);
        } catch (UnknownHostException e) {
            System.out.println("Error al obtener la IP del cliente"); // Manejo de Errores
        }
    }

    private MensajeriaP2P() {
    }

    public static MensajeriaP2P getInstance() {
        if (instance == null) {
            instance = new MensajeriaP2P();
        }
        return instance;
    }

    public boolean verificarPuerto(int port) {
        System.out.println("Verificando: " + this.ip_client+":"+port);
        try (Socket socket = new Socket()) {
            InetSocketAddress conexion = new InetSocketAddress(this.ip_client, port);
            socket.connect(conexion, 10000);
            return true;
        } catch (IOException e) {
            System.out.println("Error al verificar el puerto: " + e.getMessage());
            return false;
        }
    }

    public boolean iniciarSesion(String nickname,String puerto) {
        boolean result = false;
        if (instance.verificarPuerto(Integer.parseInt(puerto))) {
            // TO DO: Crear instancia de Usuario y abrir vista de inicio
            result = true;
            System.out.println("Puerto CORRECTO");
        } else {
            //TO DO: Agregar mensaje y ventana de error
            result = false;
            System.out.println("Puerto INCORRECTO");
        }
        return result;
    }
}