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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("AGREGAR_CONTACTO")) {

            String nombre = this.vista_inicio.getNombreContacto();
            String ip_contacto = this.vista_inicio.getIpContacto();
            String puerto_comunicacion = this.vista_inicio.getPuertoComunicacionContacto();
        }
        else if (e.getActionCommand().equalsIgnoreCase("ABRIR_CONVERSACION")) {

        }

    }
}
