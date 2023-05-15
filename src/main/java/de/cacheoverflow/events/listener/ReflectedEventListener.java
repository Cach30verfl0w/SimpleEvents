package de.cacheoverflow.events.listener;

import de.cacheoverflow.events.annotations.EventHandler;
import de.cacheoverflow.events.bus.IEventBus;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectedEventListener {

    private final List<IEventListener> listeners = new ArrayList<>();
    private final IEventBus bus;

    private ReflectedEventListener(@NotNull final IEventBus bus, @NotNull final Object object) {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        this.bus = bus;
        try {
            for (Method method : object.getClass().getMethods()) {
                if (!method.isAnnotationPresent(EventHandler.class))
                    continue;

                if (method.getParameterCount() != 1)
                    continue;

                HandleBasedMethodListener methodListener = new HandleBasedMethodListener(object, lookup.unreflect(method));
                bus.register(methodListener);
                this.listeners.add(methodListener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ReflectedEventListener create(@NotNull final IEventBus bus, @NotNull final Object object) {
        return new ReflectedEventListener(bus, object);
    }

    public void unregister() {
        this.listeners.forEach(this.bus::unregister);
    }

    public static class HandleBasedMethodListener implements IEventListener {

        private final Object object;
        private final MethodHandle handle;

        public HandleBasedMethodListener(@NotNull final Object object, @NotNull final MethodHandle handle) {
            this.object = object;
            this.handle = handle;
        }

        @Override
        public void call(@NotNull Object event) {
            try {
                this.handle.invoke(this.object, event);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public @NotNull Class<?> getEventType() {
            return this.handle.type().parameterType(0);
        }

    }

}
