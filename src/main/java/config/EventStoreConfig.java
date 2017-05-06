package config;

/**
 * Created by toraj on 05/06/2017.
 */
public class EventStoreConfig {

    static int port = 1113;
    static String ip = "127.0.0.1";

    public static int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
