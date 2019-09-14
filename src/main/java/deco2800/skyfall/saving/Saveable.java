package deco2800.skyfall.saving;

public interface Saveable<T extends AbstractMemento> {
    public abstract T save();
    public abstract void load(T saveInfo);
}
