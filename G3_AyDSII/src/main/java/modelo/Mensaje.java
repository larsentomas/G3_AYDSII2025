// Mensaje.java

package modelo;

import java.time.LocalDateTime;

public class Mensaje {
    private Usuario remitente;
    private String contenido;
    private LocalDateTime timestamp;
    private boolean nuevaConversacion;
    // Esta variable se usa porque cuando creo una nueva conversacion le envia un "mensaje" al usuario con esta flag en true
    // Para indicar que tiene que crearle la conversacion al usuario y tiene que agendar el contacto en caso necesario

    public Mensaje(Usuario remitente, String contenido) {
        this.remitente = remitente;
        this.contenido = contenido;
        this.timestamp = LocalDateTime.now();
        this.nuevaConversacion = false;
    }

    public Mensaje(Usuario remitente, String contenido, boolean flag) {
        this.remitente = remitente;
        this.contenido = contenido;
        this.timestamp = LocalDateTime.now();
        this.nuevaConversacion = flag;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return remitente + " =" + contenido;
    }

    public boolean isNuevaConversacion() {
        return nuevaConversacion;
    }
}
