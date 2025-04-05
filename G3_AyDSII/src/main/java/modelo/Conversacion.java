package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversacion implements Serializable {
    private Usuario usuario;

    private ArrayList<Mensaje> mensajes;

    public Conversacion(Usuario usuario) {
        this.usuario = usuario;
        this.mensajes = new ArrayList<>();
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMensajes(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public String toString() {
        return usuario.toString();
    }
}
