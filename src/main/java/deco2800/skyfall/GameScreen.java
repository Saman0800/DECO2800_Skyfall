package deco2800.skyfall;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.handlers.KeyboardManager;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.renderers.PotateCamera;
import deco2800.skyfall.renderers.OverlayRenderer;
import deco2800.skyfall.renderers.Renderer3D;
import deco2800.skyfall.worlds.*;
import deco2800.skyfall.managers.SoundManager;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameScreen implements Screen,KeyDownObserver {
	private final Logger LOG = LoggerFactory.getLogger(Renderer3D.class);
	@SuppressWarnings("unused")
	private final SkyfallGame game;
	/**
	 * Set the renderer.
	 * 3D is for Isometric worlds
	 * Check the documentation for each renderer to see how it handles WorldEntity coordinates
	 */
	Renderer3D renderer = new Renderer3D();
	OverlayRenderer rendererDebug = new OverlayRenderer();
	AbstractWorld world;
	static Skin skin;

	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.
	 */
	PotateCamera camera, cameraDebug;

	public Stage stage = new Stage(new ExtendViewport(1280, 720));

	long lastGameTick = 0;

	ShaderProgram shaderProgram;
	

	public GameScreen(final SkyfallGame game, boolean isHost) {
		/* Create an example world for the engine */
		this.game = game;

		GameManager gameManager = GameManager.get();

		// Create main world
		if (!isHost) {
			world = new ServerWorld();
			GameManager.get().getManager(NetworkManager.class).connectToHost("localhost", "duck1234");
		} else {
			world = new RocketWorld();
			GameManager.get().getManager(NetworkManager.class).startHosting("host");
		}

		gameManager.setWorld(world);

		// Add first peon to the world
		camera = new PotateCamera(1920, 1080);
		cameraDebug = new PotateCamera(1920, 1080);

		/* Add the window to the stage */
		GameManager.get().setSkin(skin);
		GameManager.get().setStage(stage);
		GameManager.get().setCamera(camera);

		/* Play BGM */
		try {
			SoundManager.backgroundGameMusic("resources/sounds/Forest Day.wav");
			SoundManager.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

		PathFindingService pathFindingService = new PathFindingService();
		GameManager.get().addManager(pathFindingService);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(GameManager.get().getManager(KeyboardManager.class));
		multiplexer.addProcessor(GameManager.get().getManager(InputManager.class));
		Gdx.input.setInputProcessor(multiplexer);
		
		GameManager.get().getManager(KeyboardManager.class).registerForKeyDown(this);

		String vertexShader =
				"#version 330 core\n"+
			"attribute vec4 a_position;\n"+
		"attribute vec4 a_color;\n"+
		"attribute vec2 a_texCoord0;\n"+

		"uniform mat4 u_projTrans;\n"+

		"varying vec4 v_color;\n"+
		"varying vec2 v_texCoords;\n"+

		"out vec2 oPosition;\n"+

		"void main() {\n"+
		"v_color = a_color;\n"+
		"v_texCoords = a_texCoord0;\n"+
		"gl_Position = u_projTrans * a_position;\n"+
		"oPosition = a_position.xy;\n"+
		"};\n";
		String fragmentShader =
				"#version 330 core\n"+
		"#ifdef GL_ES\n"+
		"precision mediump float;\n"+
		"#endif\n"+

		"in vec2 oPosition;\n"+

		"varying vec4 v_color;\n"+
		"varying vec2 v_texCoords;\n"+
		"uniform sampler2D u_texture;\n"+
		"uniform mat4 u_projTrans;\n"+

		"void main() {\n"+
		"	vec4 colora = texture(u_texture, v_texCoords).rgba;\n"+
		"	if (colora.w < 0.2) {discard;};\n"+
		"	vec3 color = colora.xyz;\n"+
		"	float gray = (color.r + color.g + color.b) / 3.0;\n"+
		"	vec3 grayscale = 0.5*color+ 0.5*vec3(oPosition/100, 1.0);\n"+
		"	vec3 ambient = 0.2*color;\n"+
		"	float dStr = clamp((1 - length(oPosition)/200), 1.0, 0.0);\n"+
		"	vec3 direct = dStr*vec3(1.0, 0.729, 0.33)*color;\n"+
		"	gl_FragColor = vec4(ambient + direct, 1.0);\n"+
		"}";

		shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
		shaderProgram.pedantic=false;
		for (int i = 0; i < 20; i++) {
			System.out.print("-");
		}
		System.out.print("\n");
		System.out.print(shaderProgram.getLog());
	}

	/**
	 * Renderer thread
	 * Must update all displayed elements using a Renderer
	 */
	@Override
	public void render(float delta) {
		handleRenderables();
		
		moveCamera();
			
		cameraDebug.position.set(camera.position);
		cameraDebug.update();
		camera.update();

		SpriteBatch batchDebug = new SpriteBatch();
		batchDebug.setProjectionMatrix(cameraDebug.combined);
		
		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		// Clear the entire display as we are using lazy rendering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		rerenderMapObjects(batch, camera);
		rendererDebug.render(batchDebug, cameraDebug);
		
		/* Refresh the experience UI for if information was updated */
		stage.act(delta);
		stage.draw();
		batch.dispose();
	}

	private void handleRenderables() {
		if (System.currentTimeMillis() - lastGameTick > 20) {
			lastGameTick = System.currentTimeMillis();
			GameManager.get().onTick(0);
		}
	}

	/**
	 * Use the selected renderer to render objects onto the map
	 */
	private void rerenderMapObjects(SpriteBatch batch, OrthographicCamera camera) {
		batch.setShader(shaderProgram);
		renderer.render(batch, camera);
	}

	@Override
	public void show() {

	}

	/**
	 * Resizes the viewport
	 *
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		
		cameraDebug.viewportWidth = width;
		cameraDebug.viewportHeight = height;
		cameraDebug.update();		
	}

	@Override
	public void pause() {
		//do nothing
	}

	@Override
	public void resume() {
		//do nothing
	}

	@Override
	public void hide() {
		//do nothing
	}
	
	/**
	 * Disposes of assets etc when the rendering system is stopped.
	 */
	@Override
	public void dispose() {
		// Don't need this at the moment
		System.exit(0);
	}

	@Override
	public void notifyKeyDown(int keycode) {
		if (keycode == Input.Keys.F12) {
			GameManager.get().debugMode = !GameManager.get().debugMode;
		}

		if (keycode == Input.Keys.F5) {
			world = new RocketWorld();
			AbstractEntity.resetID();
			Tile.resetID();
			GameManager gameManager = GameManager.get();
			gameManager.setWorld(world);

			// Add first peon to the world
			world.addEntity(new Peon(0f, 0f, 0.05f, "Side Piece", 10));
		}
		
		if (keycode == Input.Keys.F11) { // F11
			GameManager.get().showCoords = !GameManager.get().showCoords;
			LOG.info("Show coords is now {}", GameManager.get().showCoords);
		}
		
		
		if (keycode == Input.Keys.C) { // F11
			GameManager.get().showCoords = !GameManager.get().showCoords;
			LOG.info("Show coords is now {}", GameManager.get().showCoords);
		}

		if (keycode == Input.Keys.F10) { // F10
			GameManager.get().showPath = !GameManager.get().showPath;
			LOG.info("Show Path is now {}", GameManager.get().showPath);
		}

		if (keycode == Input.Keys.F3) { // F3
			// Save the world to the DB
			DatabaseManager.saveWorld(null);
		}

		if (keycode == Input.Keys.F4) { // F4
			// Load the world to the DB
			DatabaseManager.loadWorld(null);
		}
	}
	
	public void moveCamera() {
	//timmeh to fix hack.  // fps is not updated cycle by cycle
		float normilisedGameSpeed = (60.0f/Gdx.graphics.getFramesPerSecond());
				
		int goFastSpeed = (int) (5 * normilisedGameSpeed *camera.zoom);
		
		if (!camera.isPotate()) {
			
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				goFastSpeed *= goFastSpeed * goFastSpeed;
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				camera.translate(-goFastSpeed, 0, 0);
			}
	
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				camera.translate(goFastSpeed, 0, 0);
			}
	
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				camera.translate(0, -goFastSpeed, 0);
			}
	
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				camera.translate(0, goFastSpeed, 0);
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
				camera.zoom *=1-0.01*normilisedGameSpeed;
				if (camera.zoom < 0.5) {
					camera.zoom = 0.5f;
				}
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
				camera.zoom *=1+0.01*normilisedGameSpeed;
			}
		}
		
	}
}