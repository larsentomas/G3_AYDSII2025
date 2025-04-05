package modelo;


import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {
    private Usuario emisor;
    private String contenido;
    private Date timestamp;
    private boolean activa;

    public Mensaje(Usuario emisor, String contenido) {
        this.emisor = emisor;
        this.contenido = contenido;
        this.timestamp = new Date();
        this.activa = true;
    }

    public Mensaje(Usuario emisor, String contenido, boolean esActiva) {
        this.emisor = emisor;
        this.contenido = contenido;
        this.timestamp = new Date();
        this.activa = esActiva;
    }

    public boolean isActiva() {
        return activa;
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
        return contenido;
    }
}

