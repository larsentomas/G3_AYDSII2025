package controlador;

import modelo.Contacto;
import modelo.Conversacion;
import sistema.MensajeriaP2P;
import vista.VistaInicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener  {
    private VistaInicio vista_inicio;

    public Controlador(VistaInicio vi) {

        this.vista_inicio = vi;
        initController();
    }

    private void initController() {
        this.vista_inicio.getBtnNuevaConversacion().setActionCommand("NUEVA_CONVERSACION");
        this.vista_inicio.getBtnNuevaConversacion().addActionListener(this);
        this.vista_inicio.getBtnNuevoContacto().setActionCommand("MOSTRAR_AGREGAR_CONTACTO");
        this.vista_inicio.getBtnNuevoContacto().addActionListener(this);
        this.vista_inicio.getEnviarMensaje().setActionCommand("ENVIAR_MENSAJE");
        this.vista_inicio.getEnviarMensaje().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("NUEVA_CONVERSACION")) {
            Contacto contacto = vista_inicio.mostrarModalNuevaConversacion();
            if (contacto != null) {
                MensajeriaP2P.getInstance().crearConversacion(contacto);
                vista_inicio.actualizarListaConversaciones();
            }
        }
        else if (e.getActionCommand().equalsIgnoreCase("ENVIAR_MENSAJE")) {
            String mensaje = this.vista_inicio.getMensaje();
            Conversacion conversacion = this.vista_inicio.getConversacionSeleccionada();
            if (conversacion != null && mensaje != null && !mensaje.trim().isEmpty()) {
                MensajeriaP2P.getInstance().enviarMensaje(mensaje, conversacion);
                vista_inicio.actualizarPanelChat(conversacion);
            }
        } else if (e.getActionCommand().equalsIgnoreCase("MOSTRAR_AGREGAR_CONTACTO")) {
            String[] contactoInfo = vista_inicio.mostrarModalAgregarContacto();
            if (contactoInfo != null) {
                String nickname = contactoInfo[0];
                String ip = contactoInfo[1];
                String puerto = contactoInfo[2];
                MensajeriaP2P.getInstance().agregarContacto(nickname, ip, Integer.parseInt(puerto));
            }
        }

    }
}
