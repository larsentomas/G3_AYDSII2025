package modelo;

import sistema.MensajeriaP2P;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;

public class EnviadorMensajes implements Runnable {

    private Socket socket;
    private UsuarioLogueado usuarioLogueado;

    public EnviadorMensajes(Socket socket, UsuarioLogueado usuarioLogueado) {
        this.socket = socket;
        this.usuarioLogueado = usuarioLogueado;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            try {
                Mensaje mensajito = (Mensaje) in.readObject();
                System.out.println("Se leyo el mensaje " + mensajito);

                MensajeriaP2P.getInstance().recibirMensaje(mensajito);

            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("End of stream reached: " + e.getMessage());
        }
    }
}
