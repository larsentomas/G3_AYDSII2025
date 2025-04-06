package controlador;

import modelo.Conversacion;
import modelo.Usuario;
import sistema.MensajeriaP2P;
import vista.VistaInicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controlador implements ActionListener  {
    private VistaInicio vista_inicio;

    public Controlador(VistaInicio vi) {

        this.vista_inicio = vi;
        initController();
    }

    private void initController() {
        this.vista_inicio.getBtnNuevaConversacion().setActionCommand("NUEVA_CONVERSACION");
        this.vista_inicio.getBtnNuevaConversacion().addActionListener(this);
        this.vista_inicio.getBtnNuevoContacto().setActionCommand("AGREGAR_CONTACTO");
        this.vista_inicio.getBtnNuevoContacto().addActionListener(this);
        this.vista_inicio.getEnviarMensaje().setActionCommand("ENVIAR_MENSAJE");
        this.vista_inicio.getEnviarMensaje().addActionListener(this);
        this.vista_inicio.setPanelchat(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MensajeriaP2P sistema = MensajeriaP2P.getInstance();
        if (e.getActionCommand().equalsIgnoreCase("NUEVA_CONVERSACION")) {
            // Si no hay contacto para iniciar conversacion --> error
            if (sistema.getUser().getContactosSinConversacion().isEmpty()) {
                vista_inicio.mostrarModalError("No hay contactos disponibles para iniciar una conversación.");
                return;
            }

            // Si hay contacto para iniciar conversacion --> mostrar modal
            Usuario usuario_conversacion = vista_inicio.mostrarModalNuevaConversacion();

            // Si el usuario no es null --> crear conversacion
            if (usuario_conversacion != null) {
                Conversacion c = sistema.getUser().crearConversacion(usuario_conversacion);

                // Actualizar la lista de conversaciones y la vista para que muestre la conversaciona ctual
                vista_inicio.actualizarListaConversaciones();
                vista_inicio.setConversacion(c);
                System.out.println("Conversacion creada con " + usuario_conversacion.getNickname());
            }
        } else if (e.getActionCommand().equalsIgnoreCase("ENVIAR_MENSAJE")) {
            String mensaje = vista_inicio.getMensaje();
            // Validar que el mensaje no esté vacío
            if (mensaje.isEmpty()) {
                vista_inicio.mostrarModalError("El mensaje no puede estar vacío.");
                return;
            }
            // Validar que haya una conversación activa
            Conversacion conversacion = vista_inicio.getConversacionActiva();
            if (conversacion != null) {
                sistema.enviarMensaje(conversacion, mensaje, true);
                vista_inicio.limpiarcampos();
            } else {
                vista_inicio.mostrarModalError("No hay conversación seleccionada.");
            }
        } else if (e.getActionCommand().equalsIgnoreCase("AGREGAR_CONTACTO")) {
            String[] contactoInfo = vista_inicio.mostrarModalAgregarContacto();
            if (contactoInfo != null) {
                String nickname = contactoInfo[0];
                String ip = contactoInfo[1];
                String puerto = contactoInfo[2];

                sistema.agendarContacto(nickname, ip, Integer.parseInt(puerto));
            }
        }

    }
}
