package deco2800.skyfall.saving;

public interface Saveable<T extends AbstractMemento> {
    public abstract T save() throws SaveException;
    public abstract void load(T memento);
}
