package ezjson.structures;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Information from www.json.org:
 *
 * An object is an unordered set of name/value pairs.
 * An object begins with { (left brace) and ends with } (right brace).
 * Each name is followed by : (colon) and the name/value pairs are separated by , (comma).
 */
public class JsonObject {
    private HashMap<String, Object> attributes;

    public JsonObject() {
        attributes = new HashMap<>();
    }

    public JsonObject(Object key, Object value) {
        attributes = new HashMap<>();
        add(key, value);
    }

    public JsonObject(HashMap<Object, Object> map) {
        attributes = new HashMap<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public void add(Object key, Object value) {
        String keyString = key.toString();
        if (attributes.containsKey(keyString)) {
            throw new IllegalArgumentException(
             "Key \"" + keyString + "\" was already assigned to value: "
             + valueString(attributes.get(keyString), 0));
        }
        if (Util.isValidValue(value)) {
            attributes.put(key.toString(), value);
        }
    }

    public String toStringPretty() {
        return "";
    }

    public String tabDepth(int depth) {
        StringBuilder tabBuilder = new StringBuilder();
        while (depth > 0) {
            tabBuilder.append("\t");
            depth--;
        }
        return tabBuilder.toString();
    }

    public String valueString(Object value, int depth) {
        if (value instanceof String) {
            return "\"" + value.toString() + "\"";
        }
        else if (value instanceof JsonObject) {
            StringBuilder jsonObjectString = new StringBuilder();
            jsonObjectString.append("{\n");
            if (!((JsonObject) value).attributes.isEmpty()) {
                Iterator<Map.Entry<String, Object>> iterator = ((JsonObject) value).attributes.entrySet().iterator();
                Map.Entry<String, Object> first = iterator.next();
                jsonObjectString.append(tabDepth(depth+1) + "\"" + first.getKey() + "\": " + valueString(first.getValue(), depth+1));
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    jsonObjectString.append(",\n"+ tabDepth(depth+1) +"\"" + next.getKey() + "\": " + valueString(next.getValue(), depth+1));
                }
            }
            jsonObjectString.append("\n" + tabDepth(depth) + "}");
            return jsonObjectString.toString();
        }
        return value.toString();
    }

    public void saveToFile(String filename) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            fw.write("{\n");
            if (!attributes.isEmpty()){
                Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator();
                Map.Entry<String, Object> first = iterator.next();
                fw.write("\t\"" + first.getKey() + "\": " + valueString(first.getValue(), 1));
                while (iterator.hasNext()){
                    Map.Entry<String, Object> next = iterator.next();
                    fw.write(",\n\t\"" + next.getKey() + "\": " + valueString(next.getValue(), 1));
                }
            }
            fw.write("\n}\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("integer$!", 2);
        jsonObject.add("strings?@", "test");
        jsonObject.add("double-=+", new Double(2.09));
        jsonObject.add("boolean", true);

        JsonObject jsonObject1 = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonObject jsonObject3 = new JsonObject();

        jsonObject2.add("nested", new JsonObject("test", "testValue"));
        jsonObject2.add("value", "value");

        jsonObject1.add("nested", jsonObject2);
        jsonObject1.add("2nd Object", jsonObject3);
        jsonObject.add("object?", jsonObject1);

        jsonObject.saveToFile("test.json");
    }
}
