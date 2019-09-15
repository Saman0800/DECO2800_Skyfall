package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.google.gson.annotations.Expose;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.NetworkManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.renderers.Renderable;
import deco2800.skyfall.util.BodyEditorLoader;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * A AbstractEntity is an item that can exist in both 3D and 2D worlds
 * AbstractEntities are rendered by Render2D and Render3D An item that does not
 * need to be rendered should not be a WorldEntity
 */
public abstract class AbstractEntity implements Comparable<AbstractEntity>, Renderable {
	private final transient Logger log =
			LoggerFactory.getLogger(AbstractEntity.class);

	private static final String ENTITY_ID_STRING = "entityID";
	
	@Expose
	private String objectName = null;
		
	private static int nextID = 0;

	public static void resetID() {
		nextID = 0;
	}

	protected static int getNextID() {
		return nextID++;
	}

	protected HexVector position;
	private int height;
	private float colRenderLength;
	private float rowRenderLength;

    //Box2D properties
    private Body body;
    protected Fixture fixture;
    private Boolean isCollidable;

	@Expose
	private String texture = "error_box";

	@Expose
	private int entityID = 0;

	private int renderOrder = 0;

	//For animations
	/**
	 * Maps animations roles to animation names
	 */
    protected Map<AnimationRole, Map<Direction, AnimationLinker>> animations;

	/**
	 * Default textures in each direction
	 */
	protected Map<Direction, String> defaultDirectionTextures = new HashMap<>();
	/**
     * The animation to be run
	 */
	private AnimationLinker toBeRun = null;
    /**
     * The current direction to that the entity is facing
     */
	private Direction currentDirection = Direction.EAST;
    /**
     * The current state of the entity
     */
	private AnimationRole currentState = AnimationRole.NULL;
	protected float scale = 1f;
	/**
	 * Constructor for an abstract entity
	 * @param col the col position on the world
	 * @param row the row position on the world
	 * @param renderOrder the height position on the world
     */

	public AbstractEntity(float col, float row, int renderOrder) {
		this(col, row, renderOrder, 1f, 1f);
		entityID = AbstractEntity.getNextID();
		this.setObjectName(ENTITY_ID_STRING);
		this.renderOrder = renderOrder;
        animations = new HashMap<>();
    }

	public AbstractEntity(float col, float row, int renderOrder,
						  String fixtureDef) {
		this(col, row, renderOrder, 1f, 1f, fixtureDef);
		entityID = AbstractEntity.getNextID();
		this.setObjectName(ENTITY_ID_STRING);
		this.renderOrder = renderOrder;
		animations = new HashMap<>();
	}

	public AbstractEntity() {
		this.position = new HexVector();
		this.colRenderLength = 1f;
		this.rowRenderLength = 1f;
		this.setObjectName(ENTITY_ID_STRING);
        animations = new HashMap<>();
        changeCollideability(true);
		this.initialiseBox2D(position.getCol(), position.getRow());
    }

	/**
	 * Constructor for an abstract entity
	 * @param col the col position on the world
	 * @param row the row position on the world
	 * @param height the height position on the world
	 * @param colRenderLength the rendered length in col direction
	 * @param rowRenderLength the rendered length in the row direction
     */
	public AbstractEntity(float col, float row, int height,
						  float colRenderLength, float rowRenderLength) {
		this.position = new HexVector(col, row);
		this.height = height;
		this.colRenderLength = colRenderLength;
		this.rowRenderLength = rowRenderLength;
		this.entityID = AbstractEntity.getNextID();
        changeCollideability(true);
		this.initialiseBox2D(position.getCol(), position.getRow());
    }

	public AbstractEntity(float col, float row, int height,
						  float colRenderLength, float rowRenderLength,
						  String fixtureDefFile) {
		this.position = new HexVector(col, row);
		this.height = height;
		this.colRenderLength = colRenderLength;
		this.rowRenderLength = rowRenderLength;
		this.entityID = AbstractEntity.getNextID();
		changeCollideability(true);
		this.initialiseBox2D(position.getCol(), position.getRow(), fixtureDefFile);
	}

	/**
	 * Get the column position of this AbstractWorld Entity
	 */
	public float getCol() {
		return position.getCol();
	}

	/**
	 * Get the row position of this AbstractWorld Entity
	 */
	public float getRow() {
		return position.getRow();
	}

	/**
	 * Get the Z position of this AbstractWorld Entity
	 * 
	 * @return The Z position
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the col coordinate for the entity
     */
	public void setCol(float col) {
		this.position.setCol(col);
	}

	/**
	 * Sets the row coordinate for the entity
	 */
	public void setRow(float row) {
		this.position.setRow(row);
	}

	/**
	 * Sets the height coordinate for the entity
	 */
	public void setHeight(int z) {
		this.height = z;
	}

	/**
	 * sets the position of the entity in the world
	 * @param col the x coordinate for the entity
	 * @param row the y coordinate for the entity
     * @param height the z coordinate for the entity
     */
	public void setPosition(float col, float row, int height) {
		setCol(col);
		setRow(row);
		setHeight(height);
	}

	public float getColRenderWidth() {
		return colRenderLength;
	}

	public float getRowRenderWidth() {
		return rowRenderLength;
	}
	
	public void setRenderOrder(int newLevel) {
		this.renderOrder = newLevel;
	}
	
	public int getRenderOrder() {
		return renderOrder;
	} 
	
	@Override 
	public int compareTo(AbstractEntity otherEntity) {
		return this.renderOrder - otherEntity.getRenderOrder();
	}

	@Override
	public float getColRenderLength() {
		return this.colRenderLength;
	}

	@Override
	public float getRowRenderLength() {
		return this.rowRenderLength;
	}

	/**
	 * Gives the string for the texture of this entity. This does not mean the
	 * texture is currently registered
	 * 
	 * @return texture string
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Sets the texture string for this entity. Check the texture is registered with
	 * the TextureRegister
	 * 
	 * @param texture
	 *            String texture id
	 */
	public void setTexture(String texture) {
		this.texture = texture;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof AbstractEntity)) {
			return false;
		}

		AbstractEntity entity = (AbstractEntity) other;
		return height == entity.height &&
				Float.compare(entity.colRenderLength, colRenderLength) == 0 &&
				Float.compare(entity.rowRenderLength, rowRenderLength) == 0 &&
				entityID == entity.entityID &&
				Objects.equals(texture, entity.texture) &&
				Objects.equals(position, entity.position);
	}

	@Override
	public int hashCode() {
		int result = position != null ? position.hashCode() : 0;
		result = 31 * result + (texture != null ? texture.hashCode() : 0);
		return result;
	}

	/**
	 * Gets the distance from an abstract entity
	 * @param e the abstract entity
	 * @return the distance as a float
     */
	public float distance(AbstractEntity e) {
		return this.position.distance(e.position);
	}
	

	public HexVector getPosition() {
		return position;
	}

	public abstract void onTick(long i);

	/**
	 * Set objectID (If applicable)
	 *
	 * @param name of object
	 */
	public void setObjectName(String name) {
		this.objectName = name;
	}

	/**
	 * Get objectID (If applicable)
	 *
	 * @return Name of object
	 */
	public String getObjectName() {
		return this.objectName;
	}
	
	
	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int id) {
		this.entityID = id;
	}

    public void dispose() {
        body.destroyFixture(fixture);

        GameManager.get().getManager(NetworkManager.class).deleteEntity(this);
        GameManager.get().getWorld().getEntities().remove(this);
    }

	/**
	 * Gets the associate animation with an animation role
	 * @param type Animation role to get animation for
	 * @return animation name
	 */

	private AnimationLinker getAnimationLinker(AnimationRole type, Direction direction) {
	    if (animations.containsKey(type)) {
			Map<Direction, AnimationLinker> roleMap = animations.get(type);
			return roleMap.getOrDefault(direction, roleMap.getOrDefault(Direction.DEFAULT,null));
        }

        return null;
    }

    /**
     * Gets the current animation to be run
     * @return
     */
	public AnimationLinker getToBeRun() {
		return toBeRun;
	}

	/**
	 * Called on creation of the entity for setting up the entities collision
	 * Uses default fixture shape/size of a small circle
	 *
	 * @param x the entities x coordinate
	 * @param y the entities y coordinate
	 */
    private void initialiseBox2D(float x, float y) {
		defineBody(x, y);
		defineFixture();
    }

	/**
	 * Called on creation of the entity for setting up the entities collision
	 * Defines the fixture size/shape based on a .JSON file
	 *
	 * @param x the entities x coordinate
	 * @param y the entities y coordinate
	 * @param fixtureDefFile file path the .JSON file
	 */
    public void initialiseBox2D(float x, float y, String fixtureDefFile){
		defineBody(x, y);
		defineFixture(fixtureDefFile);
	}

	/**
	 * Defines the properties for the entity's body using default values
	 * Custom values can be set in the individual entities constructor after the
	 * body has been created
	 *
	 * @param x the entities x coordinate
	 * @param y the entities y coordinate
	 */
	private void defineBody(float x, float y){
		World box2DWorld = GameManager.get().getManager(PhysicsManager.class).getBox2DWorld();

		//Creates the body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		body = box2DWorld.createBody(bodyDef);

		body.setFixedRotation(true);
		body.setLinearDamping(0.8f);
		body.setUserData(this);
	}

	/**
	 * Defines the fixture using default properties and default shape
	 * of a small circle.
	 * Custom values can be set in the individual entities constructor after the
	 * fixture has been created.
	 */
	public void defineFixture(){
		CircleShape shape = new CircleShape();
		shape.setRadius(0.4f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixture = body.createFixture(fixtureDef);

		//Sets up if the entity can be collided with
		//True by default
		fixture.setSensor(!isCollidable);

		shape.dispose();
	}

	/**
	 * Defines the body's fixture with default values which can be changes in
	 * the entity's constructor after the fixture is created.
	 * Sets the fixtures shape and size based on a .JSON file
	 *
	 * @param fixtureDefFile file path to .JSON file defining the fixture
	 */
	public void defineFixture(String fixtureDefFile){
		BodyEditorLoader loader =
				new BodyEditorLoader(Gdx.files.internal("resources/HitBoxes/" + fixtureDefFile +
						"HitBox.JSON"));

		PhysicsManager manager = new PhysicsManager();
		World world = manager.getBox2DWorld();
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.KinematicBody;
		body = world.createBody(bd);

		PolygonShape shape = new PolygonShape();

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.3f;

		fixture = body.createFixture(fixtureDef);
		fixture.setSensor(!isCollidable);

		loader.attachFixture(body, fixtureDefFile, fixtureDef, scale);
		//TODO: Add code for defining code for custom body shape
	}

	/**
	 * Controls if the entity can be collided with
	 *
	 * @param collidable boolean value if entities can collide with this entity
	 */
	
    public void changeCollideability(Boolean collidable){
        isCollidable = collidable;
        if (fixture != null) {
			fixture.setSensor(!isCollidable);
		}
    }

	/**
	 * Called every time the entity is involved in a collision
	 * Should be overwritten for each entity where something should occur
	 * during a collision
	 *
	 * @param other the other object involved in the collision
	 */
	public void handleCollision(Object other) {
        //Does nothing as collision logic should be case specific
			log.info("I was hit: " + this.getClass() + "\n by: " + other.getClass());
    }

    /**
     * Sets the current animation to be run to null
     */
	public void setGetToBeRunToNull() {
		setCurrentState(AnimationRole.NULL);
		//toBeRun = null;
	}

    /**
     * The current direction that the object is facing
     * @return Direction that the entity is facing
     */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

    /**
     * The current state of the object
     * @return The state of the object
     */
	public AnimationRole getCurrentState() {
		return currentState;
	}

    /**
     * Set the current direction and also updates the animation to be run
     * variable
     * @param currentDirection new direction that the entity is facing
     */
	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
		toBeRun = getAnimationLinker(this.currentState, this.currentDirection);
	}

    /**
     * Set the current state and also updates the animation to be run
     * variable
     * @param currentState new direction that the entity is facing
     */
	public void setCurrentState(AnimationRole currentState) {
		this.currentState = currentState;
		toBeRun = getAnimationLinker(this.currentState, this.currentDirection);
	}

    /**
     * Adds an animation to the animation map
     * @param role State
     * @param currentDirection Direction
     * @param animationLinker Animation object
     */
    protected void addAnimations(AnimationRole role, Direction currentDirection, AnimationLinker animationLinker) {
    	if (role == AnimationRole.NULL) {
      		log.info("Don't Set AnimationRole.NULL to an animation");
			return;
    	}
	    animations.putIfAbsent(role, new HashMap<>());
        Map<Direction, AnimationLinker> direction = animations.get(role);
        direction.put(currentDirection, animationLinker);
    }

    /**
     * Gets the default direction texture
	 *
     * @return Texture name of the direction texture
     */
    public String getDefaultTexture() {
        return defaultDirectionTextures.getOrDefault(getCurrentDirection(), "Not Found");
    }


    /**
     * How much to scale the texture by.
     * Used in MainCharacter to scale down the texture
     * @return Scale multiplicative factor.
     */
    public float getScale() {
        return scale;
    }
	/**
	 * Gets the body for the entity
	 *
	 * @return body of the entity
	 */
	public Body getBody(){
		return body;
	}

}


