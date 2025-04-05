package modelo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Conversacion {
    private Contacto contacto;
    private List<Mensaje> mensajes;
    private Socket socketEnvio;
    private Socket socketRecepcion;

    public Conversacion(Contacto contacto, Cliente user) throws IOException {
        try {
            this.contacto = contacto;
            this.mensajes = new ArrayList<>();
            this.socketEnvio =  new Socket(this.contacto.getIp(), this.contacto.getPuerto());
            this.socketRecepcion = new Socket(user.getIp(), user.getPuerto());
        } catch (Exception e) {
            System.err.println("Error al obtener la dirección IP del socket de recepción: " + e.getMessage());
        }

    }

    public Contacto getContacto() {
        return contacto;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }

    public Socket getEnvio() {
        return socketEnvio;
    }

    public Socket getRecepcion() {
        return socketRecepcion;
    }

    @Override
    public String toString() {
        return contacto.getNickname();
    }
}
