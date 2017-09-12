package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;
import net.sf.json.JSONObject;

import java.lang.reflect.*;
import java.util.HashMap;

public class DumpFields {

        public static <T> HashMap<String, Object> inspect(Object obj) {
            Class<?> klazz = obj.getClass();
            Field[] fields = klazz.getDeclaredFields();
            HashMap<String, Object> fieldMap = new HashMap<>();
            System.out.printf("%d fields:%n", fields.length);
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
            // the hashmap from inspect()
            // is put into a json
            JSONObject json = new JSONObject();
            json.putAll(inspect(obj));
            System.out.println(json);
            return json.toString();
        }

        
}

