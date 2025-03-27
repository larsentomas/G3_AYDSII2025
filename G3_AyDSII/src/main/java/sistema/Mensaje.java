package sistema;

import java.time.LocalDateTime;

public class Mensaje {
    private String contenido;
    private LocalDateTime timestamp;
    private boolean enviado; // true si fue enviado, false si fue recibido.

    public Mensaje(String contenido, boolean enviado) {
        this.contenido = contenido;
        this.timestamp = LocalDateTime.now();
        this.enviado = enviado;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isEnviado() {
        return enviado;
    }

    @Override
    public String toString() {
        String tipo = enviado ? "Enviado" : "Recibido";
        return "[" + timestamp + "] " + tipo + ": " + contenido;
    }
}
