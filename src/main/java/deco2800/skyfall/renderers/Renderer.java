package deco2800.skyfall.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renderers provide an interface between lists of objects and the LibGDX Graphics Backend.
 * Renderers can be used to create both 2D and 3D worlds currently, however further renderers could be created to render
 * other objects such as minimaps etc.
 * @Author Tim Hadwen
 */
public interface Renderer {

    /**
     * Renderers must be able to render a world given a list of Entities and a batch renderer
     * @param batch Batch to render onto
     */
    void render(SpriteBatch batch, OrthographicCamera camera);
}