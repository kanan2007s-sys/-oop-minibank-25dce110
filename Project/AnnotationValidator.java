
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidator {

    public static List<String> validate(Object object) {
        List<String> errors = new ArrayList<>();

        if (object == null) {
            errors.add("Object cannot be null");
            return errors;
        }

        Class<?> type = object.getClass();

        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive annotation =
                            field.getAnnotation(Positive.class);

                        if (!(value instanceof Number) ||
                            ((Number) value).doubleValue() <= 0) {
                            errors.add(field.getName() + " " +
                                       annotation.message());
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                        if (value instanceof String &&
                            ((String) value).length() > annotation.value()) {
                            errors.add(field.getName() +
                                       " exceeds maximum length " +
                                       annotation.value());
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Cannot access field: " + field.getName());
                }
            }

            type = type.getSuperclass();
        }

        return errors;
    }
}
