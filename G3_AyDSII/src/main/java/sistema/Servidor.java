package sistema;

import java.io.*;
import java.net.*;

public class Servidor {
    private int puerto;

    public Servidor(String direccionIP, int puerto) {
        this.puerto = puerto;
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor activo en el puerto: " + puerto);

            while (true) {
                java.net.Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String mensaje = in.readLine();
                System.out.println("Mensaje recibido: " + mensaje);
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
