package deco2800.skyfall.managers;

/**
 * :tumbleweed:
 */
public abstract class AbstractManager {

    public AbstractManager() {

    }

    public AbstractManager(AbstractManager abstractManager) {
        this.am = abstractManager;
    }

    public int add(int x, int y) {
        return x + y;
    }

}
