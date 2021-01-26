package com.gordonfromblumberg.games.common.event;

import com.gordonfromblumberg.games.common.utils.Pools;

public abstract class EventHandler<T extends Event> {
    public void handle(T event) {
        process(event);

        Pools.free(event);
    };

    protected abstract void process(T event);
}
