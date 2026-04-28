package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class VikingService {
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    private final List<Consumer<List<Viking>>> listeners = new CopyOnWriteArrayList<>();

    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }

    public void addListener(Consumer<List<Viking>> listener) {
        listeners.add(listener);
    }

    private void notifyDataChanged() {
        listeners.forEach(listener -> listener.accept(List.copyOf(vikings)));
    }

    public List<Viking> findAll() {
        return List.copyOf(vikings);
    }

    public Viking createRandomViking() {
        Viking viking = vikingFactory.createRandomViking();
        vikings.add(viking);
        notifyDataChanged();
        return viking;
    }

    public Viking addViking(Viking viking) {
        Viking storedViking = viking.id() == null
                ? new Viking(
                UUID.randomUUID(),
                viking.name(),
                viking.age(),
                viking.heightCm(),
                viking.hairColor(),
                viking.beardStyle(),
                viking.equipment()
        )
                : viking;
        vikings.add(storedViking);
        notifyDataChanged();
        return storedViking;
    }

    public Viking findById(UUID id) {
        return vikings.stream()
                .filter(viking -> viking.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Viking with id " + id + " not found"));
    }

    public Viking updateViking(UUID id, Viking updatedViking) {
        for (int i = 0; i < vikings.size(); i++) {
            Viking current = vikings.get(i);
            if (current.id().equals(id)) {
                Viking replacement = new Viking(
                        id,
                        updatedViking.name(),
                        updatedViking.age(),
                        updatedViking.heightCm(),
                        updatedViking.hairColor(),
                        updatedViking.beardStyle(),
                        updatedViking.equipment()
                );
                vikings.set(i, replacement);
                notifyDataChanged();
                return replacement;
            }
        }
        throw new NoSuchElementException("Viking with id " + id + " not found");
    }

    public void deleteViking(UUID id) {
        boolean removed = vikings.removeIf(viking -> viking.id().equals(id));
        if (!removed) {
            throw new NoSuchElementException("Viking with id " + id + " not found");
        }
        notifyDataChanged();
    }
}