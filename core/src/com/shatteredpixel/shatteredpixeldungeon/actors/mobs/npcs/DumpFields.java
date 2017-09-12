package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;
import net.sf.json.JSONObject;

import java.io.*;
import java.lang.reflect.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class DumpFields {

        public static <T> HashMap<String, Object> inspect(Object obj) {
            Class<?> klazz = obj.getClass();
            Field[] fields = klazz.getDeclaredFields();
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

        public static void relayStatus (Object obj, Socket socket) {
            // relay the status (the json from dumpAsJSON)
            // to a peer through a socket
            try {
                OutputStream out = socket.getOutputStream();
                PrintStream printer = new PrintStream(out, true);
                String jsonMsg = dumpAsJSON(obj);
                System.out.println(jsonMsg);
                printer.println (jsonMsg);

            } catch (IOException e) {
                System.out.println("Error on relay: " + e.getMessage());
            }
        }

        
}

