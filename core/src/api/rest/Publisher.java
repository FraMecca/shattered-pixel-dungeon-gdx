package api.rest;

import org.json.JSONObject;
import org.zeromq.ZMQ;

public class Publisher {

    static final ZMQ.Context context = ZMQ.context(1);
    static final ZMQ.Socket server = context.socket(ZMQ.PUB);

    public static void initialize(Integer port) {
        String id = "serv1";
        String ip = "192.168.0.141";
        String serverIp = "localhost";
        boolean f = server.bind("tcp://localhost:" + port.toString());
        System.out.println(f);
    }

    public static void sendStatus(byte[] data) {
        boolean fc = server.send(data);
    }
}
