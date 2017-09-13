package api.rest;
import net.sf.json.JSONObject;

import java.io.*;
import java.lang.reflect.*;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import org.zeromq.*;
import org.zeromq.ZMQ.*;

public class DumpFields {

    static final Context context = ZMQ.context(1);
    static final ZMQ.Socket client = context.socket(ZMQ.REQ);
    static public boolean connected = false;

    public static <T> HashMap<String, Object> inspect(Object obj) {
        Class<?> klazz = obj.getClass();
        Field[] fields = klazz.getFields();
        HashMap<String, Object> fieldMap = new HashMap<>();
        for (Field field : fields) {
            try {

                fieldMap.put(
                        field.getName(),
                        field.get(obj)
                );
            } catch (IllegalAccessException e) {
            }
        }
        return fieldMap;
    }

    public static String dumpAsJSON (Object obj) {
        // dump object fields as json, meaning
        // the hashmap from inspect()
        // is put into a json
        JSONObject json = new JSONObject();
        json.putAll(inspect(obj));
        return json.toString();
    }

    public static byte[] getStatus (Object e) throws IOException {
        // serialize object through Serializable interface
        // encode to base64 in order to send it over json
        // could use post requests, but lets use gets
        ByteArrayOutputStream fileOut =
                new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(e);
        out.close();
        fileOut.close();
        byte[] encoded = Base64.getEncoder().encode(fileOut.toByteArray());
        return encoded;
    }

    public static void connect () {
        if (!connected) {
            client.connect("tcp://localhost:5555");
            connected = true;
        }
    }

    public static boolean relayStatus (Object obj) {
        connect();
        try {
            byte[] data = getStatus(obj);
            client.send(data);
            System.out.println("Sent status");
            // zeromq must recv an ack in order to unlock status when client / server comm link
            byte[] rec = client.recv();
            System.out.println(rec);
            return true;
        } catch (Exception e) {
            System.err.println("Can't send status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

