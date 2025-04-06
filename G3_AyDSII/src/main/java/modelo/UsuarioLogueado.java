package modelo;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;

public class UsuarioLogueado extends Usuario implements Serializable {
    private ArrayList<Usuario> contactos;
    private ArrayList<Conversacion> conversaciones;

    public UsuarioLogueado(String name, String ip, int port) {
        super(name, ip, port);
        this.contactos = new ArrayList<>();
        this.conversaciones = new ArrayList<>();
    }

    // agregar

    public void agregarContacto(Usuario usuario) {
        contactos.add(usuario);
        System.out.println("Contacto agregado: " + usuario.getNickname() + " a la lista de contactos de " + this.getNickname());
    }

    public void agregarConversacion(Conversacion conversacion) {
        this.conversaciones.add(conversacion);
    }

    // getters y setters

    public ArrayList<Conversacion> getConversaciones() {
        return conversaciones;
    }

    public ArrayList<Conversacion> getConversacionesActivas() {
        ArrayList<Conversacion> convActivas = new ArrayList<>();
        for (Conversacion c: conversaciones) {
            if (c.isActiva()) {
                convActivas.add(c);
            }
        }
        return convActivas;
    }

    public ArrayList<Usuario> getContactosSinConversacion() {
        ArrayList<Usuario> contactosSinConversacion = new ArrayList<>();
        if (conversaciones != null) {
            for (Usuario usuario : contactos) {
                boolean tieneConversacion = false;
                for (Conversacion conversacion : conversaciones) {
                    if (conversacion.getUsuario().getIp().equals(usuario.getIp()) && conversacion.getUsuario().getPuerto() == usuario.getPuerto()) {
                        tieneConversacion = true;
                        break;
                    }
                }
                if (!tieneConversacion) {
                    contactosSinConversacion.add(usuario);
                }
            }
        } else {
            contactosSinConversacion = contactos;
        }
        return contactosSinConversacion;
    }

    public Conversacion crearConversacion(Usuario usuario) {
        Conversacion conversacion = new Conversacion(usuario);
        this.agregarConversacion(conversacion);
        return conversacion;
    }

    public void desactivarConversaciones() {
        for (Conversacion conversacion : conversaciones) {
            conversacion.setActiva(false);
        }
    }

    public ArrayList<Usuario> getContactos() {
        return contactos;
    }

    public void setContactos(ArrayList<Usuario> contactos) {
        this.contactos = contactos;
    }

    public void setConversaciones(ArrayList<Conversacion> conversaciones) {
        this.conversaciones = conversaciones;
    }

}
