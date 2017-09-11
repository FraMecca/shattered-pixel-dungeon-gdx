package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;
import java.lang.reflect.*;
import java.util.HashMap;
import com.google.gson.Gson;

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
            Gson g = new Gson();
            String json = g.toJson(obj);
            System.out.println(json);
            return json;
        }
}

