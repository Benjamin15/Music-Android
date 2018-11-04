package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model;

public class User {
    private String name;
    private String ip;
    private String mac;


    public User(String name, String ip, String mac) {
        this.name = name;
        this.ip = ip;
        this.mac = mac;
    }

    public User(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
