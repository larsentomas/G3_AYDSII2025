// MensajeriaP2P.java
package sistema;

import controlador.Controlador;
import controlador.LoginControlador;
import modelo.*;
import vista.VistaLogin;
import vista.VistaInicio;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    public void iniciarUsuario(String usuario, String puerto) throws UnknownHostException {

        UsuarioLogueado usuarioLogueado = new UsuarioLogueado(usuario, InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(puerto));
        this.usuarioLogueado = usuarioLogueado;
        new Thread(new ManejadorMensajes(usuarioLogueado)).start();
    }

    // Metodos de la clase

    public Conversacion crearConversacion(Usuario usuario) {
        return usuarioLogueado.crearConversacion(usuario);
    }

    public void agendarContacto(String nickname, String ip, int puerto) {
        Usuario newUsuario = new Usuario(nickname, ip, puerto);
        usuarioLogueado.agregarContacto(newUsuario);
    }

    public void enviarMensaje(Conversacion c, String mensaje) {
        Mensaje m = new Mensaje(usuarioLogueado, mensaje);
        new Thread(new RecibidorMensajes(c.getUsuario().getIp(), c.getUsuario().getPuerto(), m)).start();
        c.agregarMensaje(m);
        MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
    }

    public void recibirMensaje(Mensaje mensajito) {
        Conversacion c = null;

        // Si no lo tengo agendado --> creo conversacion y lo agendo
        Usuario remitente = existeUsuario(mensajito.getEmisor().getIp(), mensajito.getEmisor().getPuerto());
        if (remitente == null) {
            usuarioLogueado.agregarContacto(mensajito.getEmisor());
            c = usuarioLogueado.crearConversacion(mensajito.getEmisor());
        } else {
            // Busco si existe conversacion
            for (Conversacion conversacion : usuarioLogueado.getConversaciones()) {
                if (conversacion.getUsuario().equals(remitente)) {
                    c = conversacion;
                    break;
                }
            }

            if (c == null) {
                // Si no existe conversacion, la creo
                c = usuarioLogueado.crearConversacion(mensajito.getEmisor());
            }
        }

        // Agrego el mensaje a la conversacion
        c.agregarMensaje(mensajito);

        MensajeriaP2P.getInstance().getVistaInicio().actualizarListaConversaciones();
        MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
    }

    private Usuario existeUsuario(String ip, int puerto) {
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