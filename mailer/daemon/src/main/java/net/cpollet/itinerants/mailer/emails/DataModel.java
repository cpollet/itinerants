package net.cpollet.itinerants.mailer.emails;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by cpollet on 13.05.17.
 */
public class DataModel {
    private final List<Map<String, Object>> variables;

    public DataModel(List<Map<String, Object>> variables) {
        this.variables = variables;
    }

    public Object get(String key) {
        return variables.stream()
                .filter(map -> map.containsKey(key))
                .findFirst()
                .orElse(Collections.emptyMap())
                .get(key);
    }
}
