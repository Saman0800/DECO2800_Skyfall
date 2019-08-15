package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class PlayerPeon extends Peon implements TouchDownObserver {

    private static final String WALK_NORMAL = "people_walk_normal";

    private SoundManager soundManager = GameManager.get().getManager(SoundManager.class);

    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);
    }


	@Override
    public void onTick(long i) {
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow left clicks to move player
        if (button != 0) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        this.task = new MovementTask(this, new HexVector (clickedPosition[0],clickedPosition[1]));
    }
}
