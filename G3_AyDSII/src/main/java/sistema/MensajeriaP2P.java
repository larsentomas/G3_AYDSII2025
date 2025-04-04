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
    private String ip_client;
    private Cliente user;
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

            // Copilot pero tengo entendido que no funciona porque no me llegan los mensajes
            instance.iniciarServidor(5000, vista_inicio); // Use the appropriate port
        } catch (UnknownHostException e) {
            System.out.println("Error al obtener la IP del cliente");
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

    public Conversacion crearConversacion(Contacto contacto) {
        Conversacion conv = new Conversacion(contacto);
        this.user.getConversaciones().add(conv);
        System.out.println("Conversacion creada con " + contacto.getNickname());

        // Notificar al contacto que se creo una conversacion con el
        Mensaje mensaje = new Mensaje(this.user, "Nueva conversacion creada", true);
        user.enviarMensaje(mensaje, contacto);

        return conv;
    }

    // Chequea si el mensaje recibido es verdaderamente un mensaje o una alerta de que se creo una conversacion
    public void procesarMensajeRecibido(Mensaje mensaje, VistaInicio vi) {
        Conversacion conversacion = buscarConversacion(mensaje.getRemitente());
        if (mensaje.isNuevaConversacion()) {
            // Crea conversacion si no existe (no deberia)
            if (conversacion == null) {
                Usuario tipito = mensaje.getRemitente();
                Contacto contacto_tipito = new Contacto(tipito.getNickname(), tipito.getIp(), tipito.getPuerto());
                conversacion = new Conversacion(contacto_tipito);
                this.user.getConversaciones().add(conversacion);
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

    public void enviarMensaje(String mensaje, Conversacion conversacion) {
        if (this.user.getConversaciones().contains(conversacion)) {
            Mensaje m = new Mensaje(this.user, mensaje);
            user.enviarMensaje(m, conversacion.getContacto());
            conversacion.agregarMensaje(m);
        } else {
            System.out.println("Error al enviar el mensaje");
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

    public boolean iniciarSesion(String nickname, String puerto) {
        boolean result = false;
        if (instance.verificarPuerto(Integer.parseInt(puerto))) {
            this.user = new Cliente(nickname, this.ip_client, Integer.parseInt(puerto));
            result = true;
            System.out.println("Puerto CORRECTO");
        } else {
            System.out.println("Puerto INCORRECTO");
        }
        return result;
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