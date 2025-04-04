package modelo;

import java.util.ArrayList;
import java.util.List;

public class Conversacion {
    private Contacto contacto;
    private List<Mensaje> mensajes;

    public Conversacion(Contacto contacto) {
        this.contacto = contacto;
        this.mensajes = new ArrayList<>();
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

    @Override
    public String toString() {
        return contacto.getNickname();
    }
}
