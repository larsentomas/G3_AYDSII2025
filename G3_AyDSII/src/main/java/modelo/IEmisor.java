package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public interface IEmisor {
    void enviarMensaje(Mensaje mensaje, Socket socket);
}
