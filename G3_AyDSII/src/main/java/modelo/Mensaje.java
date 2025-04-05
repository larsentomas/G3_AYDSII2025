package modelo;


import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {
    private Usuario emisor;
    private String contenido;
    private Date timestamp;

    public Mensaje(Usuario emisor, String contenido) {
        this.emisor = emisor;
        this.contenido = contenido;
        this.timestamp = new Date();
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public String getContenido() {
        return contenido;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp.getTime() + "] " + emisor + ": " + contenido;
    }
}

