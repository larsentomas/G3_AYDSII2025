// LoginController.java
package controlador;

import sistema.MensajeriaP2P;
import modelo.Servidor;
import vista.Login;
import vista.VistaInicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class LoginController {
    private Login login;
    private VistaInicio vistaInicio;

    public LoginController(Login login, VistaInicio vistaInicio) {
        this.login = login;
        this.vistaInicio = vistaInicio;
        initController();
    }

    private void initController() {
        login.getBotonInicio().setActionCommand("INICIAR_SESION");
        login.getBotonInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("INICIAR_SESION".equals(e.getActionCommand())) {
                    String user = login.getUser();
                    String puerto = login.getPuerto();
                    try {
                        if (MensajeriaP2P.getInstance().iniciarSesion(user, puerto)) {
                            vistaInicio.setVisible(true);
                            login.setVisible(false);
                        }
                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}