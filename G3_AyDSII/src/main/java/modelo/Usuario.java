package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nickname;
    private String ip;
    private int puerto;

    public Usuario(String nickname, String ip, int puerto) {
        this.nickname = nickname;
        this.ip = ip;
        this.puerto = puerto;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public String toString() {
        return nickname + '(' +
                ip + ':' +
                puerto + ')';
    }
}
