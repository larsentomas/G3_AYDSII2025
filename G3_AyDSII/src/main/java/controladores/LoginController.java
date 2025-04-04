// LoginController.java
package controladores;

import sistema.MensajeriaP2P;
import sistema.Servidor;
import vistas.Login;
import vistas.VistaInicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                    if (MensajeriaP2P.getInstance().iniciarSesion(user, puerto)) {
                        // Iniciar el servidor para recibir mensajes
                        new Thread(() -> {
                            Servidor servidor = new Servidor(Integer.parseInt(puerto));
                            servidor.iniciar();
                        }).start();
                        // Mostrar la ventana de inicio
                        vistaInicio.setVisible(true);
                        login.setVisible(false);
                    }
                }
            }
        });
    }
}