package modelo;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class RecibidorMensajes implements Runnable {
    private String ip;
    private int port;
    private Mensaje mensaje;

    public RecibidorMensajes(String ip, int port, Mensaje mensaje) {
        this.ip = ip;
        this.port = port;
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(mensaje);;
            out.flush();
        } catch (Exception e) {
            System.err.println("❌ Error al enviar mensaje a " + ip + ":" + port);
            e.printStackTrace();
        }
    }
}

