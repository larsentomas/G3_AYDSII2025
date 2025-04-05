// MensajeriaP2P.java
package sistema;

import controlador.Controlador;
import controlador.LoginController;
import modelo.*;
import vista.Login;
import vista.VistaInicio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

public class MensajeriaP2P {
    private Cliente user;
    private static MensajeriaP2P instance = null;

    public static void main(String[] args){
        instance = MensajeriaP2P.getInstance();

        VistaInicio vistaInicio = new VistaInicio();
        Login login = new Login();
        LoginController controladorLogin = new LoginController(login, vistaInicio);
        Controlador controlador = new Controlador(vistaInicio);

        // Copilot pero tengo entendido que no funciona porque no me llegan los mensajes
        instance.iniciarServidor(5000, vistaInicio); // Use the appropriate port
    }

    private MensajeriaP2P() {
    }

    public static MensajeriaP2P getInstance() {
        if (instance == null) {
            instance = new MensajeriaP2P();
        }
        return instance;
    }

    public boolean iniciarSesion(String nickname, String puerto) throws UnknownHostException {
        try {
            boolean result = false;
            InetAddress localHost = InetAddress.getLocalHost();
            if (instance.verificarPuerto(Integer.parseInt(puerto))) {
                this.user = new Cliente(nickname, localHost.getHostAddress(), Integer.parseInt(puerto));
                result = true;
            }
            //Asignar algún mensaje de error significativo si el puerto está ocupado/fuera de rango,etc
            return result;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Contacto agregarContacto(String nombre, String ip, int puerto) {
        Contacto contacto = null;
        if (verificarPuertoContacto(ip, puerto)) {
            contacto = new Contacto(nombre, ip, puerto);
            this.user.agregarContacto(contacto);
            System.out.println(contacto.getNickname() + " agregado como contacto exitosamente");
        } else {
            System.out.println("Fallo la creacion del contacto");
        }
        return contacto;
    }

    public Conversacion crearConversacion(Contacto contacto) throws IOException {
        try{
            Conversacion conv = new Conversacion(contacto, this.user);
            this.user.getConversaciones().add(conv);
            System.out.println("Conversacion creada con " + contacto.getNickname());

            // Notificar al contacto que se creo una conversacion con el
            return conv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Chequea si el mensaje recibido es verdaderamente un mensaje o una alerta de que se creo una conversacion
    public void procesarMensajeRecibido(Mensaje mensaje, VistaInicio vi) {
        Conversacion conversacion = buscarConversacion(mensaje.getRemitente());
        if (mensaje.isNuevaConversacion()) {
            // Crea conversacion si no existe (no deberia)
            if (conversacion == null) {
                Usuario tipito = mensaje.getRemitente();
                Contacto contactoTipito = new Contacto(tipito.getNickname(), tipito.getIp(), tipito.getPuerto());
//                conversacion = new Conversacion(contactoTipito);
//                this.user.getConversaciones().add(conversacion);
                System.out.println("Conversacion creada con " + mensaje.getRemitente().getNickname());
            }
        } else {
            // Mensajes normalitos
            if (conversacion != null) {
                conversacion.agregarMensaje(mensaje);
                vi.actualizarPanelChat(conversacion);
            }
        }
    }

    // Esta es para verificar puerto e ip de contacto cuando lo quiero agregar
    // Permite agregar contactos con puertos libres, porq ni idea
    public boolean verificarPuertoContacto(String ip, int port) {
        System.out.println("Verificando: " + ip + ":" + port);
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 10000);
            return socket.isConnected();
        } catch (IOException e) {
            System.out.println("Error al verificar el puerto: " + e.getMessage());
            return false;
        }
    }

    // Esta es solo para verficiar el puerto del usuario cuando inicia sesion
    public boolean verificarPuerto(int port) {
        String ipClient = this.user.getIp();
        try (Socket socket = new Socket()) {
            InetSocketAddress conexion = new InetSocketAddress(ipClient, port);
            socket.connect(conexion, 10000);
            return true;
        } catch (IOException e) {
            System.out.println("Error al verificar el puerto: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Object> getContactosSinConversacion() {
        ArrayList<Object> contactosSinConversacion = new ArrayList<>();
        for (Contacto contacto : this.user.getContactos()) {
            boolean tieneConversacion = false;
            for (Conversacion conversacion : this.user.getConversaciones()) {
                if (conversacion.getContacto().equals(contacto)) {
                    tieneConversacion = true;
                    break;
                }
            }
            if (!tieneConversacion) {
                contactosSinConversacion.add(contacto);
            }
        }
        return contactosSinConversacion;
    }

    public Cliente getUser() {
        return this.user;
    }

    // Puro copilot, ni idea
    public void iniciarServidor(int puerto, VistaInicio vi) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Mensaje mensaje = (Mensaje) ois.readObject();
                    procesarMensajeRecibido(mensaje, vi);
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Conversacion buscarConversacion(Usuario remitente) {
        for (Conversacion conversacion : this.user.getConversaciones()) {
            if (conversacion.getContacto().equals(remitente)) {
                return conversacion;
            }
        }
        return null;
    }
}