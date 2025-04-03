package controladores;

import sistema.MensajeriaP2P;
import sistema.Usuario;
import vistas.ILogin;
import vistas.Login;
import vistas.VistaInicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private VistaInicio vista_inicio;
    private ILogin ilogin;

    public LoginController(ILogin login, VistaInicio vista_inicio) {
        this.vista_inicio = vista_inicio;
        this.ilogin = login;


        this.ilogin.setActionListener((ActionListener) this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("INICIAR_SESION")) {
            MensajeriaP2P sistema = MensajeriaP2P.getInstance();
            String puerto = this.ilogin.getPuerto();
            String user = this.ilogin.getUser();
            if (sistema.iniciarSesion(user, puerto)) {
                this.cerrarVentana();
            }
        }
    }

    private void cerrarVentana() {
        this.ilogin.setVisibleVentana(false);
        this.ilogin.limpiarcampos();
        this.vista_inicio.setVisible(true); //este luego habria que hacer la interfaz para esta ventana

    }

}