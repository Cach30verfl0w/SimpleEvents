package de.cacheoverflow.events.listener;

import org.jetbrains.annotations.NotNull;

public interface IEventListener {

    void call(final @NotNull Object event);

    @NotNull Class<?> getEventType();

}
