package deco2800.skyfall.managers;

/**
 * :tumbleweed:
 */
public abstract class AbstractManager {
    private AbstractManager am;

    public AbstractManager() {

    }

    public AbstractManager(AbstractManager abstractManager) {
        this.am = abstractManager;
    }

    public int add(int x, int y) {
        return x + y;
    }

}
