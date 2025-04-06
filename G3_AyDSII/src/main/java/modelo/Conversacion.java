package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversacion implements Serializable {
    private Usuario usuario;
    private boolean activa;
    private boolean notificado;


    private ArrayList<Mensaje> mensajes;

    public Conversacion(Usuario usuario) {
        this.usuario = usuario;
        this.mensajes = new ArrayList<>();
        this.activa = true;
    }

    public void agregarMensaje(Mensaje mensaje) {
        this.setNotificado(true);
        mensajes.add(mensaje);
    }

    public boolean isNotificado() {
        return notificado;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setNotificado(boolean notificado) {
        this.notificado = notificado;
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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return usuario.toString();
    }
}
