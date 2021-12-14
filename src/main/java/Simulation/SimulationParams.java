package Simulation;

import java.util.Map;

public class SimulationParams {
    private static final String CONFIG_PATH = "config.json";
    public static  final Map<String, Integer> paramsMap;
    static {
        paramsMap = JsonParser.readSimulationParams(CONFIG_PATH);
    }

    public static Integer getField(String filedName) {
        return paramsMap.computeIfAbsent(filedName, k -> {
            throw new IllegalArgumentException("There is no filed: " + k );
        });
    }

    public static void setField(String fieldName, int value) {
        paramsMap.put(fieldName, value);
    }

    public static Map<String, Integer> getParamsMap() {
        return paramsMap;
    }
}
