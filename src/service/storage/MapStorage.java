package service.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Selvin
 * on 25.07.2014.
 */
public class MapStorage {

    private Map<Integer, Long> MAP = new HashMap<>();

    public MapStorage(Map<Integer, Long> map) {
        this.MAP = map;
    }

    public synchronized Long get(Integer id) {
        if(exist(id)) return MAP.get(id);
        else return 0L;
    }

    public synchronized void add(Integer id, Long value) {
        if(exist(id)) MAP.put(id, MAP.get(id) + value);
        else MAP.put(id, value);
    }

    private boolean exist(Integer id) {
        return MAP.get(id) != null;
    }

    public synchronized Map<Integer, Long> getAll() {
        return MAP;
    }
}
