package modelo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario implements IEmisor, IReceptor {
    private List<Contacto> contactos;
    private List<Conversacion> conversaciones;

    public Cliente(String nickname, String direccionIP, int puerto) {
        super(nickname, direccionIP, puerto);
        this.contactos = new ArrayList<>();
        this.conversaciones = new ArrayList<>();
    }

    //Ahora deberia enviar mensajes de tipo Mensaje y no String
    public void enviarMensaje(Mensaje mensaje, Socket socket) {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(mensaje);
            System.out.println("Mensaje enviado: " + mensaje);
        } catch (IOException e) {
            System.err.println("Error al enviar el mensaje: " + e.getMessage());
        }
    }

    //Cada conversacion deberia tener un socket creado aca corriendo en un hilo, escuchando a ver si el contacto envia msj
    public void recibirMensajes() {
        new Thread(() -> {
            try(Socket socket = new Socket(getIp(), getPuerto())){
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Mensaje mensaje;
                while ((mensaje = (Mensaje) in.readObject()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                }
            } catch (IOException|ClassNotFoundException e) {
                System.err.println("Error al recibir el mensaje: " + e.getMessage());
            }
        }).start();
    }

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
        System.out.println("Contacto registrado exitosamente: " + contacto.getNickname());
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public List<Conversacion> getConversaciones() {
        return conversaciones;
    }

//    public boolean enviarMensaje(Mensaje mensaje, Contacto contacto) {
//        try (Socket socket = new Socket(contacto.getIp(), contacto.getPuerto())) {
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            out.println(mensaje);
//            System.out.println("Mensaje enviado a " + contacto.getIp() + ":" + contacto.getPuerto());
//            return true;
//        } catch (IOException e) {
//            System.err.println("Error al enviar el mensaje: " + e.getMessage());
//        }
//        return false;
//    }

}
