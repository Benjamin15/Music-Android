package client.equipe3.polymtl.ca.applicationcafebistro.model;

public class User {
    private int id;
    private String name;
    private String ip;
    private String mac;


    public User(int id, String name, String ip, String mac) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.mac = mac;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
