// Base para clientes y contactos

package modelo;

public class Usuario {
    private String nickname;
    private String ip;
    private int puerto;

    public Usuario(String alias, String ip_client, int puerto) {
        this.nickname = alias;
        this.ip = ip_client;
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return nickname + "(" + ip + ':' + puerto + ')';
    }

}
