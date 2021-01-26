package com.gordonfromblumberg.games.common.utils;

import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.Pool;

@SuppressWarnings("unchecked")
public class Pools {
    private static final IdentityMap<Class, Pool> POOL_MAP = new IdentityMap<>();

    public static <T> Pool<T> getPool(Class<T> type) {
        Pool<T> pool = POOL_MAP.get(type);
        if (pool == null) {
            pool = new Pool<T>() {
                @Override
                protected T newObject() {
                    try {
                        return type.newInstance();
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            POOL_MAP.put(type, pool);
        }
        return pool;
    }

    public static <T> T obtain(Class<T> type) {
        return getPool(type).obtain();
    }

    public static <T> void free(T object) {
        ((Pool<T>) getPool(object.getClass())).free(object);
    }
}
