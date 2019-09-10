package deco2800.skyfall.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import deco2800.skyfall.entities.AbstractEntity;

public class PhysicsManager extends AbstractManager{
    private World box2DWorld;

    public PhysicsManager(){
        // Creates the box2D world
        box2DWorld = new World(new Vector2(0, 0), true);
        initialiseCollision();
    }

    /**
     * Gets the Box2D world representation
     *
     * @return the box2D world handling physics
     */
    public World getBox2DWorld(){
        return box2DWorld;
    }

    public void initialiseCollision(){
        System.out.println("Coliision Started!!!!!");
        box2DWorld.setContactListener(new ContactListener() {
            //What happens when two bodies collide
            //Most collision is handled here
            @Override
            public void beginContact(Contact contact) {
                //Gets the two objects involved in collision
                Object o1 = contact.getFixtureA().getBody().getUserData();
                Object o2 = contact.getFixtureB().getBody().getUserData();

                System.out.println("Collision occured");

                //Runs collision handler defined in class
                if (o1.getClass() == AbstractEntity.class && !contact.getFixtureA().isSensor()){
                    AbstractEntity a1 = (AbstractEntity) o1;
                    a1.handleCollision(o2);
                }

                if (o2.getClass() == AbstractEntity.class && !contact.getFixtureB().isSensor()){
                    AbstractEntity a2 = (AbstractEntity) o2;
                    a2.handleCollision(01);
                }
            }

            //What happens when the two bodies seperates
            //Usually nothing
            @Override
            public void endContact(Contact contact) {

            }

            //What to do just before a collision
            //Usually nothing
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            //What to do just after a collision
            //Usually nothing
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
