package sistema;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nickname;
    private int puerto;
    private List<Contacto> contactos;
    private List<Conversacion> conversaciones;

    public Usuario(String nickname, int puerto) {
        this.nickname = nickname;
        this.puerto = puerto;
        this.contactos = new ArrayList<>();
        this.conversaciones = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public int getPuerto() {
        return puerto;
    }

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
        System.out.println("Contacto registrado exitosamente: " + contacto.getAlias());
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public List<Conversacion> getConversaciones() {
        return conversaciones;
    }
}
