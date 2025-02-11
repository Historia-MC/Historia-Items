package dev.boooiil.historia.items.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.Warning;
import org.jspecify.annotations.NullMarked;

import net.kyori.adventure.text.Component;

@NullMarked
public class JSONUtils {
    public static String fromValue(String key, Integer value) {
        return "\"" + key + "\":" + value;
    }

    public static String fromValue(String key, Float value) {
        return "\"" + key + "\":" + value;
    }

    public static String fromValue(String key, Long value) {
        return "\"" + key + "\":" + value;
    }

    public static String fromValue(String key, Double value) {
        return "\"" + key + "\":" + value;
    }

    public static String fromValue(String key, Boolean value) {
        return "\"" + key + "\":" + value;
    }

    public static String fromValue(String key, String value) {
        return "\"" + key + "\":" + "\"" + value + "\"";
    }

    public static String fromIntegerList(String key, List<Integer> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    public static String fromFloatList(String key, List<Float> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    public static String fromLongList(String key, List<Long> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    public static String fromDoubleList(String key, List<Double> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    public static String fromBooleanList(String key, List<Boolean> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i) + ", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    public static String fromStringList(String key, List<String> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append("\"" + values.get(i) + "\", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();

    }

    public static String fromComponentList(String key, List<Component> values) {

        StringBuilder sb = new StringBuilder();

        sb.append("\"" + key + "\":[");

        for (int i = 0; i < values.size(); i++) {
            sb.append("\"" + KyoriUtils.content(values.get(i)) + "\", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }

    public static String fromIntegerSet(String key, Set<Integer> values) {
        return fromIntegerList(key, new ArrayList<>(values));
    }

    public static String fromFloatSet(String key, Set<Float> values) {
        return fromFloatList(key, new ArrayList<>(values));
    }

    public static String fromLongSet(String key, Set<Long> values) {
        return fromLongList(key, new ArrayList<>(values));
    }

    public static String fromDoubleSet(String key, Set<Double> values) {
        return fromDoubleList(key, new ArrayList<>(values));
    }

    public static String fromBooleanSet(String key, Set<Boolean> values) {
        return fromBooleanList(key, new ArrayList<>(values));
    }

    public static String fromStringSet(String key, Set<String> values) {
        return fromStringList(key, new ArrayList<>(values));
    }

    public static String fromComponentSet(String key, Set<Component> values) {
        return fromComponentList(key, new ArrayList<>(values));
    }

    @Warning(reason = "Use at your own risk. If the value is formatted with our implementation it will not output a correctly formatted JSON.")
    public static <K, V> String fromMap(String key, Map<K, V> values) {

        StringBuilder sb = new StringBuilder();

        for (Entry<K, V> entry : values.entrySet()) {

            sb.append("\"" + entry.getKey().toString() + "\":{");
            sb.append(entry.getValue().toString());
            sb.append("}, ");

        }

        sb.setLength(sb.length() - 2);
        sb.append("}");

        return sb.toString();
    }

    @Warning(reason = "Use at your own risk. If the value is formatted with our implementation it will not output a correctly formatted JSON.")
    public static <K, V> String fromMap(String key, HashMap<K, V> values) {
        return fromMap(key, (Map<K, V>) values);
    }
}
