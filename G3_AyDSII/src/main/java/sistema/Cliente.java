package sistema;

import java.io.*;
import java.net.*;

public class Cliente {
    private String direccionIP;
    private int puerto;

    public Cliente(String direccionIP, int puerto) {
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }

    public void enviarMensaje(String mensaje) {
        try (Socket socket = new Socket(direccionIP, puerto)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(mensaje);
            System.out.println("Mensaje enviado a " + direccionIP + ":" + puerto);
        } catch (IOException e) {
            System.err.println("Error al enviar el mensaje: " + e.getMessage());
        }
    }
}
