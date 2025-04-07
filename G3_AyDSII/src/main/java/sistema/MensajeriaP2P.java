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

    /**
     * Metodo principal que inicia la aplicación de mensajería P2P.
     * <p>
     * Este metodo crea una instancia de `MensajeriaP2P`, inicializa el controlador de login y
     * el controlador principal, y agrega un listener para manejar el cerrar la ventana.
     *
     * @param args Argumentos de ejecución
     */
    public static void main(String[] args){
        instance = MensajeriaP2P.getInstance();

        controladorLogin = new LoginControlador(vistaLogin, vistaInicio);
        controlador = new Controlador(vistaInicio);

        vistaInicio.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (instance.getUser() != null) {
                    instance.cerrarSesion();
                }
            }
        });
    }

    private MensajeriaP2P() {
    }

    /**
     * Devuelve el singleton de `MensajeriaP2P`.
     *
     * @return instancia de `MensajeriaP2P`.
     */
    public static MensajeriaP2P getInstance() {
        if (instance == null) {
            instance = new MensajeriaP2P();
        }
        return instance;
    }

    public VistaInicio getVistaInicio() {
        return this.vistaInicio;
    }

    public UsuarioLogueado getUser() {
        return usuarioLogueado;
    }

    /**
     * Inicializa el usuario logueado, con el nombre de usuario y el puerto proporcionados.
     * <p>
     * El metodo verifica que el puerto ingresado esté disponible. Si lo está, se crea una nueva
     * instancia de `UsuarioLogueado` con el nombre de usuario y puerto proporcionados,
     * e inicia un nuevo hilo para manejar los mensajes entrantes.
     *
     * @param usuario Nickname del usuario
     * @param puerto Puerto del usuario
     * @return true si el usuario se inicializa correctamente, false en caso contrario.
     */
    public boolean iniciarUsuario(String usuario, String puerto) {
        try {
            if (verificarPuerto(InetAddress.getLocalHost().getHostAddress(),Integer.parseInt(puerto))) {
                System.out.println("El puerto paso la validacion");
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

    /**
     * Verifica si el puerto está disponible para enlazar.
     *
     * Este metodo intenta crear un `ServerSocket` en la dirección IP y puerto especificados.
     * Si la operación tiene éxito, significa que el puerto está disponible.
     * Si se lanza una excepción, significa que el puerto ya está en uso o no es enlazable.
     *
     * @param ip La dirección IP a verificar.
     * @param port El número de puerto a verificar.
     * @return true si el puerto está disponible, false en caso contrario.
     */
    public boolean verificarPuerto(String ip, int port) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress direccion = new InetSocketAddress(InetAddress.getByName(ip), port);
            serverSocket.setReuseAddress(true);
            serverSocket.bind(direccion);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Crea una nueva conversación con el usuario especificado.
     *
     * @param usuario El usuario con el que se desea crear la conversación.
     * @return La nueva conversación creada.
     */
    public Conversacion crearConversacion(Usuario usuario) {
        return usuarioLogueado.crearConversacion(usuario);
    }

    /**
     * Agrega un nuevo contacto a la lista de contactos del usuario logueado.
     *
     * Este metodo verifica si el contacto no es el mismo usuario logueado,
     * y que el puerto proporcionado esté escuchando.
     * Si ambas condiciones se cumplen, se crea un nuevo objeto `Usuario`,
     * y se agrega a los contactos del usuario logueado.
     *
     * @param nickname El nickname del nuevo contacto.
     * @param ip La dirección IP del nuevo contacto.
     * @param puerto El puerto del nuevo contacto.
     * @return true si el contacto se agrega correctamente, false en caso contrario.
     */
    public boolean agendarContacto(String nickname, String ip, int puerto) {
        if (!contactoDeSiMismo(ip, puerto) && validarPuertoContacto(ip, puerto) && !contactoRepetido(ip, puerto)) {
            Usuario newUsuario = new Usuario(nickname, ip, puerto);
            usuarioLogueado.agregarContacto(newUsuario);
            return true;
        }
        return false;
    }

    private boolean contactoRepetido(String ip, int puerto) {
        for (Usuario contacto : usuarioLogueado.getContactos()) {
            if (contacto.getIp().equalsIgnoreCase(ip) && contacto.getPuerto() == puerto) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el contacto es el mismo usuario logueado.
     *
     * @param ip La dirección IP del contacto.
     * @param puerto El puerto del contacto.
     * @return true si el contacto es el mismo usuario logueado, false en caso contrario.
     */
    private boolean contactoDeSiMismo(String ip, int puerto) {
        return ip.equalsIgnoreCase(this.getUser().getIp()) && puerto == this.getUser().getPuerto();
    }

    /**
     * Verifica si el puerto especificado este dentro del rango permitido y este escuchando.
     *
     * Este metodo intenta crear un `Socket` en la dirección IP y puerto especificados.
     * Si la operación tiene éxito, significa que el puerto está escuchando.
     *
     * @param ip La dirección IP a verificar.
     * @param puerto El número de puerto a verificar.
     * @return true si el puerto está escuchando, false en caso contrario.
     */
    private boolean validarPuertoContacto(String ip, int puerto) {
        if (puerto < 0 || puerto > 65535) return false;
        try (Socket socket = new Socket(ip, puerto)) {
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Envía un mensaje a la conversación especificada.
     *
     * Este metodo crea un nuevo objeto `Mensaje` con el contenido proporcionado,
     * y lo envía a través de un nuevo hilo utilizando la clase `EmisorMensajes`.
     * Si la conversación no está activa, se marca el mensaje como no activo.
     *
     * @param c La conversación a la que se desea enviar el mensaje.
     * @param mensaje El contenido del mensaje a enviar.
     * @param esActiva Indica si la conversación está activa o no.
     */
    public void enviarMensaje(Conversacion c, String mensaje, boolean esActiva) {
        Mensaje m;
        if (!esActiva) {
            m = new Mensaje(usuarioLogueado, mensaje, false);
        } else {
            m = new Mensaje(usuarioLogueado, mensaje);
        }

        try {
            new Thread(new EmisorMensajes(c.getUsuario().getIp(), c.getUsuario().getPuerto(), m)).start();
            c.agregarMensaje(m);
            MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
        } catch (Exception e) {
            MensajeriaP2P.getInstance().getVistaInicio().mostrarModalError("No se pudo enviar el mensaje: ");
        }
    }

    /**
     * Recibe un mensaje y lo agrega a la conversación correspondiente.
     *
     * Este metodo realiza las siguientes acciones cuando se recibe un mensaej:
     * Si la conversacion no existe, la crea y envia el mensaje
     * Si la conversacion es activa se agrega el mensaje a la conversación.
     * Si no está activa, se desactiva la conversación correspondiente.
     *
     * @param mensajito El mensaje recibido.
     */
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
            MensajeriaP2P.getInstance().getVistaInicio().actualizarListaConversaciones();
            if (MensajeriaP2P.getInstance().getVistaInicio().getConversacionActiva() == c) {
                MensajeriaP2P.getInstance().getVistaInicio().actualizarPanelChat(c);
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

    /**
     * Verifica si el usuario existe en la lista de contactos del usuario logueado.
     *
     * @param ip La dirección IP del usuario a verificar.
     * @param puerto El puerto del usuario a verificar.
     * @return El objeto `Usuario` si existe, null en caso contrario.
     */
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

    public void cerrarSesion() {
        for (Conversacion conversacion : instance.getUser().getConversaciones()) {
            MensajeriaP2P.getInstance().enviarMensaje(conversacion, "", false);
        }

    }
}