package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.Pool;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class Pools {
    private static final IdentityMap<Class, Pool> POOL_MAP = new IdentityMap<>();

    public static <T> void createPoolFor(Class<T> type, Function<Pool<T>, T> constructor) {
        if (!POOL_MAP.containsKey(type))
            POOL_MAP.put(type, new FunPool<T>(constructor));
    }

    public static <T> Pool<T> getPool(Class<T> type, Supplier<T> constructor) {
        Pool<T> pool = POOL_MAP.get(type);
        if (pool == null) {
            pool = new SupPool<>(constructor);
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

    static class SupPool<T> extends Pool<T> {
        private final Supplier<T> constructor;

        SupPool(Supplier<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        protected T newObject() {
            return constructor.get();
        }
    }

    static class FunPool<T> extends Pool<T> {
        private final Function<Pool<T>, T> constructor;

        FunPool(Function<Pool<T>, T> constructor) {
            this.constructor = constructor;
        }

        @Override
        protected T newObject() {
            return constructor.apply(this);
        }
    }
}
