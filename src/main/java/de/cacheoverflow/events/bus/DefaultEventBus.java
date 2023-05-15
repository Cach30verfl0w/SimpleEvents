package de.cacheoverflow.events.bus;

import de.cacheoverflow.events.listener.IEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DefaultEventBus implements IEventBus {

    private final Map<Class<?>, List<IEventListener>> registeredListeners = new HashMap<>();

    @Override
    public void register(@NotNull IEventListener listener) {
        this.registeredListeners.computeIfAbsent(listener.getEventType(), ignored -> new ArrayList<>()).add(listener);
    }

    @Override
    public void unregister(@NotNull IEventListener listener) {
        List<IEventListener> listeners = this.registeredListeners.get(listener.getEventType());
        if (listeners == null)
            return;

        listeners.add(listener);
    }

    @Override
    public <T> @NotNull T post(@NotNull T event) {
        for (IEventListener listener : this.registeredListeners.getOrDefault(event.getClass(), new ArrayList<>())) {
            listener.call(event);
        }
        return event;
    }

    @Override
    public @NotNull Map<Class<?>, List<IEventListener>> getListenersByEvents() {
        return null;
    }

}
