package deco2800.skyfall.entities;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TaskPool;
import deco2800.skyfall.tasks.AbstractTask;

/**
 * Demonstration class please delete
 */
public class BowMan extends AgentEntity {
    protected transient AbstractTask task;


    public BowMan(float col, float row) {
        super(col, row, 2, 0.05f);

        this.setTexture("spacman_blue");
        this.setHeight(1);
        this.setObjectName("spacman_blue");
    }

    @Override
    public void onTick(long i) {

        if(task != null && task.isAlive()) {
            if(task.isComplete()) {
                this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
            }
            task.onTick(i);
        } else {
            this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
        }
    }
}
