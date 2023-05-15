package de.cacheoverflow.events.listener;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class LambdaEventListener<T> implements IEventListener {

    private final Class<T> eventType;
    private final Consumer<T> eventConsumer;

    public LambdaEventListener(@NotNull final Class<T> eventType, @NotNull final Consumer<T> eventConsumer) {
        this.eventType = eventType;
        this.eventConsumer = eventConsumer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void call(@NotNull Object event) {
        assert this.eventType == event.getClass() : "Stored event type is not equal to inputted event!";
        this.eventConsumer.accept((T) event);
    }

    @Override
    public @NotNull Class<?> getEventType() {
        return this.eventType;
    }

}
