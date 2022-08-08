package api.service.rest_handlers.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestApiHandlerUtils {
    public static String getField(String regexp, String bodyParams) {
        Matcher matcher = Pattern.compile(regexp, Pattern.UNICODE_CHARACTER_CLASS).matcher(bodyParams);

        if (matcher.find()) {
            return matcher.group().split(":")[1]
                    .replaceAll("\"", "")
                    .replaceAll(" ", "");
        }
        return null;
    }


    public static long parseID(String requestPath){
        String[] parts = requestPath.split("/");
        return Long.parseLong(parts[2]);
    }


    public static void fillNullFields(Object object, Object objectFromDb) throws IllegalAccessException, NoSuchFieldException {
        Class<?> clazz1 = object.getClass();
        Class<?> clazz2 = objectFromDb.getClass();
        Field[] fields = clazz1.getDeclaredFields();
        for (Field field1: fields){
            String fieldName = field1.getName();
            field1.setAccessible(true);
            Object value1 = field1.get(object);
            if (value1 == null){
                Field field2 = clazz2.getDeclaredField(fieldName);
                field2.setAccessible(true);
                Object value2 = field2.get(objectFromDb);
                field1.set(object, value2);
                field1.setAccessible(false);
            }
        }
    }
}
