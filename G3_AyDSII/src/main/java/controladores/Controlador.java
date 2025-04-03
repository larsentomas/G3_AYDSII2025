package controladores;

import sistema.MensajeriaP2P;
import vistas.Login;
import vistas.VistaConversacion;
import vistas.VistaInicio;

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
        this.vista_inicio.getBtnNuevoContacto().setActionCommand("AGREGAR_CONTACTO");
        this.vista_inicio.getBtnNuevaConversacion().addActionListener(this);
        this.vista_inicio.getEnviarMensaje().setActionCommand("ENVIAR_MENSAJE");
        this.vista_inicio.getEnviarMensaje().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("AGREGAR_CONTACTO")) {
            System.out.println("Agregar contacto");
            /*
            POR MODAL
            String nombre = this.vista_inicio.getNombreContacto();
            String ip_contacto = this.vista_inicio.getIpContacto();
            String puerto_comunicacion = this.vista_inicio.getPuertoComunicacionContacto();
            */
        }
        else if (e.getActionCommand().equalsIgnoreCase("NUEVA_CONVERSACION")) {
            System.out.println("Crear nueva conversacion");
            MensajeriaP2P sistema = MensajeriaP2P.getInstance();
            /*
            POR MODAL
            String contacto = this.vista_inicio.getContacto()

            * */
        }
        else if (e.getActionCommand().equalsIgnoreCase("ENVIAR_MENSAJE")) {
            System.out.println("Enviar mensaje");
            /*
            String mensaje = this.vista_inicio.getMensaje();
            String contacto = this.vista_inicio.getContactoConversacion();
            * */
        }

    }
}
