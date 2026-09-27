package util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;

public final class JsonUtil {
    private JsonUtil() {
    }

    public static String toJson(Object value) throws IllegalAccessException {
        if (value == null) {
            return "null";
        }
        if (value instanceof String || value instanceof Character || value instanceof Enum<?>) {
            return quote(value.toString());
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof Map<?, ?> map) {
            StringBuilder json = new StringBuilder("{");
            Iterator<? extends Map.Entry<?, ?>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<?, ?> entry = entries.next();
                json.append(quote(String.valueOf(entry.getKey())))
                        .append(':')
                        .append(toJson(entry.getValue()));
                if (entries.hasNext()) {
                    json.append(',');
                }
            }
            return json.append('}').toString();
        }
        if (value instanceof Iterable<?> iterable) {
            StringBuilder json = new StringBuilder("[");
            Iterator<?> values = iterable.iterator();
            while (values.hasNext()) {
                json.append(toJson(values.next()));
                if (values.hasNext()) {
                    json.append(',');
                }
            }
            return json.append(']').toString();
        }
        if (value.getClass().isArray()) {
            StringBuilder json = new StringBuilder("[");
            for (int index = 0; index < Array.getLength(value); index++) {
                if (index > 0) {
                    json.append(',');
                }
                json.append(toJson(Array.get(value, index)));
            }
            return json.append(']').toString();
        }

        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Field field : value.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }
            field.setAccessible(true);
            if (!first) {
                json.append(',');
            }
            json.append(quote(field.getName())).append(':').append(toJson(field.get(value)));
            first = false;
        }
        return json.append('}').toString();
    }

    private static String quote(String value) {
        StringBuilder result = new StringBuilder("\"");
        for (char character : value.toCharArray()) {
            switch (character) {
                case '"' -> result.append("\\\"");
                case '\\' -> result.append("\\\\");
                case '\b' -> result.append("\\b");
                case '\f' -> result.append("\\f");
                case '\n' -> result.append("\\n");
                case '\r' -> result.append("\\r");
                case '\t' -> result.append("\\t");
                default -> {
                    if (character < 0x20) {
                        result.append(String.format("\\u%04x", (int) character));
                    } else {
                        result.append(character);
                    }
                }
            }
        }
        return result.append('"').toString();
    }
}