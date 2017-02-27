package ezjson.structures;

/**
 * Utility functions for validation and whatnot.
 */
public class Util {

    public static boolean isValidValue(Object value) {
        if (value instanceof JsonObject ||
             value instanceof JsonArray ||
             value instanceof Boolean ||
             value instanceof Number ||
             value instanceof String) {
            return true;
        }
        return false;
    }
}
