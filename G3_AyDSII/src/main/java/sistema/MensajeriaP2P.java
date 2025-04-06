// MensajeriaP2P.java
package sistema;

import controlador.Controlador;
import controlador.LoginControlador;
import modelo.*;
import vista.VistaLogin;
import vista.VistaInicio;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

public class MensajeriaP2P {
    private static MensajeriaP2P instance = null;
    private UsuarioLogueado usuarioLogueado;
    static VistaInicio vistaInicio = new VistaInicio();
    static VistaLogin vistaLogin = new VistaLogin();
    static LoginControlador controladorLogin = null;
    static Controlador controlador = null;

    public static void main(String[] args){
        instance = MensajeriaP2P.getInstance();

        controladorLogin = new LoginControlador(vistaLogin, vistaInicio);
        controlador = new Controlador(vistaInicio);

        vistaInicio.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (instance.getUser() != null) {
                    for (Conversacion conversacion : instance.getUser().getConversaciones()) {
                        MensajeriaP2P.getInstance().enviarMensaje(conversacion, "", false);
                    }

                }
            }
        });

    }

    // Constructores

    private MensajeriaP2P() {
    }

    public static MensajeriaP2P getInstance() {
        if (instance == null) {
            instance = new MensajeriaP2P();
        }
        return instance;
    }

    // Getter y setters

    public VistaInicio getVistaInicio() {
        return this.vistaInicio;
    }

    public UsuarioLogueado getUser() {
        return usuarioLogueado;
    }

    public boolean iniciarUsuario(String usuario, String puerto) {
        try {
            if (verificarPuerto(InetAddress.getLocalHost().getHostAddress(),Integer.parseInt(puerto))) {
                UsuarioLogueado usuarioLogueado = new UsuarioLogueado(usuario, InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(puerto));
                this.usuarioLogueado = usuarioLogueado;
                new Thread(new HandlerMensajes(usuarioLogueado)).start();
                return true;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    // Esta es solo para verficiar el puerto del usuario cuando inicia sesion
    public boolean verificarPuerto(String ip, int port) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress direccion = new InetSocketAddress(InetAddress.getByName(ip), port);
            serverSocket.setReuseAddress(true);
            serverSocket.bind(direccion);
            return true; // It's available for binding
        } catch (IOException e) {
            return false; // Something is already using it, or it's not bindable
        }
    }

    // Metodos de la clase

    public Conversacion crearConversacion(Usuario usuario) {
        return usuarioLogueado.crearConversacion(usuario);
    }

    public void agendarContacto(String nickname, String ip, int puerto) {
        if (!contactoDeSiMismo(ip, port)) {
            validarPuertoContacto(ip, puerto);
            Usuario newUsuario = new Usuario(nickname, ip, puerto);
            usuarioLogueado.agregarContacto(newUsuario);
        }
    }

    public boolean contactoDeSiMismo(String ip, int port) {
        return ip.equalsIgnoreCase(this.usuarioLo)
    }

    public void validarPuertoContacto(String ip, int puerto) {
        System.out.println("Verificando: " + ip + ":" + puerto);
        try {
            Socket socket = new Socket(ip, puerto);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void enviarMensaje(Conversacion c, String mensaje, boolean esActiva) {
        Mensaje m;
        if (!esActiva)  m = new Mensaje(usuarioLogueado, mensaje, false);
        else m = new Mensaje(usuarioLogueado, mensaje);

        new Thread(new EmisorMensajes(c.getUsuario().getIp(), c.getUsuario().getPuerto(), m)).start();
        c.agregarMensaje(m);
        MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
    }

    public void recibirMensaje(Mensaje mensajito) {
        Conversacion c = null;

        if (mensajito.isActiva()) {
            // Si no lo tengo agendado --> creo conversacion y lo agendo
            Usuario remitente = existeUsuario(mensajito.getEmisor().getIp(), mensajito.getEmisor().getPuerto());
            if (remitente == null) {
                usuarioLogueado.agregarContacto(mensajito.getEmisor());
                c = usuarioLogueado.crearConversacion(mensajito.getEmisor());
            } else {
                for (Conversacion conversacion : usuarioLogueado.getConversaciones()) {
                    if (conversacion.getUsuario().equals(remitente)) {
                        c = conversacion;
                        break;
                    }
                }
                if (c == null) {
                    c = usuarioLogueado.crearConversacion(mensajito.getEmisor());
                }
            }
            c.agregarMensaje(mensajito);
            if (MensajeriaP2P.getInstance().getVistaInicio().getConversacionActiva() == c) {
                MensajeriaP2P.getInstance().getVistaInicio().actualizarListaConversaciones();
                MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
            } else {
                MensajeriaP2P.getInstance().getVistaInicio().Notificar(c);
            }
        } else {
            // Desactivar conversacion
            for (Conversacion conversacion : usuarioLogueado.getConversaciones()) {
                if (conversacion.getUsuario().getIp().equals(mensajito.getEmisor().getIp()) &&
                        conversacion.getUsuario().getPuerto() == mensajito.getEmisor().getPuerto()) {
                    c = conversacion;
                    break;
                }
            }
            assert c != null;
            c.setActiva(false);
            MensajeriaP2P.getInstance().getVistaInicio().actualizarListaConversaciones();
            MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
        }
    }

    public Usuario existeUsuario(String ip, int puerto) {
        Usuario usuario = null;

        for (Usuario posible : this.getUser().getContactos()) {
            if (posible.getIp().equalsIgnoreCase(ip) && posible.getPuerto() == puerto) {
                usuario = posible;
                break;
            }
        }

        return usuario;
    }
}