package com.aline.aline.cache;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalCache {

    private static final ThreadLocal<Map<String, Object>> threadLocalCache = ThreadLocal.withInitial(HashMap::new);

    public static void put(String key, Object value) {
        threadLocalCache.get().put(key, value);
    }

    public static Object get(String key) {
        return threadLocalCache.get().get(key);
    }

    public static boolean containsKey(String key) {
        return threadLocalCache.get().containsKey(key);
    }

    public static void clear() {
        threadLocalCache.get().clear();
    }
}
