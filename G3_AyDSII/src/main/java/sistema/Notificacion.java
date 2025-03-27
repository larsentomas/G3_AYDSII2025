package sistema;

public class Notificacion {
    private String tipo; // Por ejemplo: "Nuevo mensaje"
    private boolean leido;

    public Notificacion(String tipo) {
        this.tipo = tipo;
        this.leido = false;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isLeido() {
        return leido;
    }

    public void marcarComoLeido() {
        this.leido = true;
    }
}
