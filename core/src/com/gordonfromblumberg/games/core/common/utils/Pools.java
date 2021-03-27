package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.Pool;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class Pools {
    private static final IdentityMap<Class, Pool> POOL_MAP = new IdentityMap<>();

    public static <T> Pool<T> getPool(Class<T> type, Supplier<T> constructor) {
        Pool<T> pool = POOL_MAP.get(type);
        if (pool == null) {
            pool = new Pool<T>() {
                @Override
                protected T newObject() {
                    return constructor.get();
                }
            };
            POOL_MAP.put(type, pool);
        }
        return pool;
    }

    public static <T> T obtain(Class<T> type) {
        return (T) POOL_MAP.get(type).obtain(); // todo check for null
    }

    public static <T> void free(T object) {
        ((Pool<T>) POOL_MAP.get(object.getClass())).free(object); // todo check for null
    }
}
