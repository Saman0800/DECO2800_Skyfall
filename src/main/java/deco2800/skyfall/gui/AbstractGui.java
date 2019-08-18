package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.skyfall.util.Vector2;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGui {
    protected boolean active = true;

    protected AbstractGui parent;
    protected List<AbstractGui> children = new ArrayList<AbstractGui>();

    protected Vector2 pos;
    protected Vector2 size;

    final String hash;

    AbstractGui(String hash) {
        this.hash = hash;
        this.parent = null;
    }

    public AbstractGui(String hash, AbstractGui parent) {
        this.hash = hash;
        this.parent = parent;
        parent.children.add(this);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getAbsolutePos() {
        if (parent == null) {
            return pos;
        } else {
            return parent.getAbsolutePos().add(pos);
        }
    }

    abstract public void update(long timeDelta);
    abstract public void render(BitmapFont font, SpriteBatch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer);

    protected void renderChildren(BitmapFont font, SpriteBatch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        for (AbstractGui element : children) {
            element.render(font, batch, camera, shapeRenderer);
        }
    }

    protected void updateChildren(long timeDelta) {
        for (AbstractGui element : children) {
            element.update(timeDelta);
        }
    }

    public void destroy(){
        if (parent == null) {
            GuiMaster.guiElementsList.remove(this);
            GuiMaster.guiElementsMap.remove(this.hash);
        } else {
            parent.children.remove(this);
        }

        for (AbstractGui element : children) {
            element.destroy();
        }
    }
}
