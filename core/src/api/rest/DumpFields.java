package api.rest;
import net.sf.json.JSONObject;

import java.io.*;
import java.lang.reflect.*;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import org.zeromq.*;
import org.zeromq.ZMQ.*;
import zmq.socket.pubsub.Pub;

public class DumpFields {

    static public boolean connected = false;
    static private boolean serverBinded;
    static private Object last = null;
    /*
    static void initialize() {
        String id = "serv1";
        String ip = "192.168.0.141";
        String serverIp = "localhost";
        Integer clientPort = 5555;
        JSONObject j = new JSONObject();
        j.put("id", id);
        j.put("port", "5555");
        j.put("ip", ip);
    }
    */

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
        byte[] encoded = Base64.getEncoder().encode(fileOut.toByteArray());
        byte[] binary = fileOut.toByteArray();
        //System.out.println("Encoded :" + encoded.length + " vs not encoded: " + fileOut.toByteArray().length);
        out.close();
        fileOut.close();
        //return encoded;
        return binary;
    }

    public static boolean relayStatus (Object obj) {
        if (last == obj) {
            return false;
        }
        try {
            byte[] data = getStatus(obj);
            System.out.println("Sent status");
            Publisher.sendStatus (data);
            return true;
        } catch (Exception e) {
            System.err.println("Can't send status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Integer sizeof (Object obj) {
        try {
            byte[] data = getStatus(obj);
            System.out.println("Sizeof " + obj.toString() + ": " + data.length);
            return data.length;
        } catch (Exception e) {
            System.err.println("Can't sizeof: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}

