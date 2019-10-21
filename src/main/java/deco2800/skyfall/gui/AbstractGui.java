package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.skyfall.util.Vector2;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGui {

    protected AbstractGui parent;
    protected List<AbstractGui> children = new ArrayList<>();

    protected Vector2 pos;
    protected Vector2 size;

    final String hash;

    /**
     * The constructor of the AbstractGui
     * @param hash the hash to be set in the GuiMaster
     */
    AbstractGui(String hash) {
        this.hash = hash;
        this.parent = null;
    }

    /**
     * The constructor of the AbstractGui
     * @param hash the hash to be set in the GuiMaster
     * @param parent the parent of the gui element
     */
    public AbstractGui(String hash, AbstractGui parent) {
        this.hash = hash;
        this.parent = parent;
        parent.children.add(this);
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public abstract void update(long timeDelta);
    public abstract void render(BitmapFont font, SpriteBatch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer);

    /**
     * Renders all the children of the gui element
     * @param font the font to be used
     * @param batch the sprite batch to render to
     * @param camera the camera to be used
     * @param shapeRenderer the shape renderer
     */
    protected void renderChildren(BitmapFont font, SpriteBatch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        for (AbstractGui element : children) {
            element.render(font, batch, camera, shapeRenderer);
        }
    }

    /**
     * Updates all the children of the gui element
     * @param timeDelta the time between the last update
     *
     */
    protected void updateChildren(long timeDelta) {
        for (AbstractGui element : children) {
            element.update(timeDelta);
        }
    }

    /**
     * Destroys the gui element and stops it from being drawn
     *
     */
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
