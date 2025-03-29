package sistema;

public class Contacto {
    private String alias;
    private String direccionIP;
    private int puerto;

    public Contacto(String alias, String direccionIP, int puerto) {
        this.alias = alias;
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }

    public String getAlias() {
        return alias;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public int getPuerto() {
        return puerto;
    }
}
