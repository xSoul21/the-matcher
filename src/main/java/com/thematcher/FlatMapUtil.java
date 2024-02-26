package com.thematcher;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FlatMapUtil {

    private FlatMapUtil() {
        throw new AssertionError("No instances for you!");
    }

    public static Map<String, Object> flatten(Map<String, Object> map) {

        return map.entrySet().stream()
                .flatMap(FlatMapUtil::flatten)
                .collect(LinkedHashMap::new, (m, e) -> m.put("/" + e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }

    private static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {

        if (entry == null) {
            return Stream.empty();
        }
        // caso in cui si una oggetto con altri oggetti "base" chiave/valore
        if (entry.getValue() instanceof Map<?, ?>) {
            return ((Map<?, ?>) entry.getValue()).entrySet().stream()
                    .flatMap(e -> flatten(new AbstractMap.SimpleEntry<>(entry.getKey() + "/" + e.getKey(), e.getValue())));
        }
        // caso inb cui sia un array "key":[]
        if (entry.getValue() instanceof List<?>) {
            List<?> list = (List<?>) entry.getValue();
            return IntStream.range(0, list.size())
                    //.mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + "/" + i, list.get(i)))
                    .mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + "/x/" + ((HashMap)list.get(i)).values().toString(), list.get(i)))
                    .flatMap(FlatMapUtil::flatten);
        }
        // caso base, e' un valore primitivo
        return Stream.of(entry);
    }
}