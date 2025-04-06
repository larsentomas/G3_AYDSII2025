package modelo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HandlerMensajes implements Runnable {

    private UsuarioLogueado usuarioLogueado;

    public HandlerMensajes(UsuarioLogueado usuario) {
        this.usuarioLogueado = usuario;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(usuarioLogueado.getPuerto())) {
            System.out.println("Escuchando en el puerto " + usuarioLogueado.getPuerto() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ReceptorMensajes(socket, this.usuarioLogueado)).start();
            }
        } catch (IOException e) {
            System.out.println("Puerto valido pero problema creando el MessageHandler, se lanzo IOException");
            //e.printStackTrace();
        }
    }
}
