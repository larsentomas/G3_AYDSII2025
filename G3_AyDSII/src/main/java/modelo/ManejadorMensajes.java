package modelo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ManejadorMensajes implements Runnable {

    private UsuarioLogueado usuarioLogueado;

    public ManejadorMensajes(UsuarioLogueado usuario) {
        this.usuarioLogueado = usuario;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(usuarioLogueado.getPuerto())) {
            System.out.println("Escuchando en el puerto " + usuarioLogueado.getPuerto() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new EnviadorMensajes(socket, this.usuarioLogueado)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
