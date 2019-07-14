package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.HexVector;
import deco2800.thomas.util.WorldUtil;

public class PlayerPeon extends Peon implements TouchDownObserver {

    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }


	@Override
    public void onTick(long i) {
        if(task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        this.task = new MovementTask(this,new HexVector ( clickedPosition[0],clickedPosition[1]));
    }
}
