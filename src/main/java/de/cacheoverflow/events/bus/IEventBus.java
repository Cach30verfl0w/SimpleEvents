package de.cacheoverflow.events.bus;

import de.cacheoverflow.events.listener.IEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface IEventBus {

    void register(final @NotNull IEventListener listener);

    void unregister(final @NotNull IEventListener listener);

    <T> @NotNull T post(final @NotNull T event);

    @NotNull Map<Class<?>, List<IEventListener>> getListenersByEvents();

}
