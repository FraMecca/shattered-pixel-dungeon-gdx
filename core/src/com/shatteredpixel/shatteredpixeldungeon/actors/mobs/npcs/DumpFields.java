package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;
import java.lang.reflect.*;

public class DumpFields {

        public static <T> void inspect(Object obj) {
            Class<?> klazz = obj.getClass();
            Field[] fields = klazz.getDeclaredFields();
            System.out.printf("%d fields:%n", fields.length);
            for (Field field : fields) {
                try {

                    System.out.printf("%s %s %s %s%n",
                            Modifier.toString(field.getModifiers()),
                            field.getType().getSimpleName(),
                            field.getName(),
                            field.get(obj)
                    );
                } catch (IllegalAccessException e) {
                }
            }
        }
    }

