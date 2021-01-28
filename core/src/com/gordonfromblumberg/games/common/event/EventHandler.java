package com.gordonfromblumberg.games.common.event;

@FunctionalInterface
public interface EventHandler<T extends Event> {
    void handle(T event);
}
