package deco2800.skyfall.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhysicsManager extends TickableManager {
    private World box2DWorld;
    private final Logger logger = LoggerFactory.getLogger(PhysicsManager.class);

    public PhysicsManager() {
        // Creates the box2D world
        box2DWorld = new World(new Vector2(0, 0), true);
        initialiseCollision();
    }

    /**
     * Gets the Box2D world representation
     *
     * @return the box2D world handling physics
     */
    public World getBox2DWorld() {
        return box2DWorld;
    }

    @Override
    public void onTick(long i) {
        getBox2DWorld().step(1 / 30f, 6, 2);
    }

    public void initialiseCollision() {
        box2DWorld.setContactListener(new ContactListener() {
            //What happens when two bodies collide
            //Most collision is handled here
            @Override
            public void beginContact(Contact contact) {
                //Gets the two objects involved in collision
                Object o1 = contact.getFixtureA().getBody().getUserData();
                Object o2 = contact.getFixtureB().getBody().getUserData();

                if (o1 == o2) {
                    logger.warn("If this is ever printed I hope I'm not the one debugging it");
                    return;
                }

                boolean reactionOccurred = !contact.getFixtureA().isSensor()
                        && !contact.getFixtureB().isSensor();

                if (reactionOccurred && (o1 instanceof MainCharacter || o2 instanceof MainCharacter)) {
                    logger.info("Collision has occurred");
                }

                //Runs collision handler defined in class
                if (o1 instanceof AbstractEntity && reactionOccurred) {
                    ((AbstractEntity) o1).handleCollision(o2);
                }

                if (o2 instanceof AbstractEntity && reactionOccurred) {
                    ((AbstractEntity) o2).handleCollision(o1);
                }
            }

            //What happens when the two bodies seperates
            //Usually nothing
            @Override
            public void endContact(Contact contact) {
                //Nothing should happen as everything is handled in begin contact
            }

            //What to do just before a collision
            //Usually nothing
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //Nothing should happen as everything is handled in begin contact
            }

            //What to do just after a collision
            //Usually nothing
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                //Nothing should happen as everything is handled in begin contact
            }
        });
        logger.info("Collision Initialised");
    }
}
