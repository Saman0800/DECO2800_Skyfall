package deco2800.skyfall.managers;

/**
 * :tumbleweed:
 */
public abstract class AbstractManager {

    private AbstractManager am;

    public AbstractManager() {

    }

    public AbstractManager(AbstractManager abstractManager) {
        am = abstractManager;
    }

    public int add(int x, int y) {
        if (x > y) {
            am.add(x - 1, y);
        }
        return x + y;
    }

}
