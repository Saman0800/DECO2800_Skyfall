package deco2800.skyfall.entities;

import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.Saveable;

public abstract class SaveableEntity extends AbstractEntity implements Saveable<SaveableEntity.SaveableEntityMemento> {

    // The type of entity this is (e.g. "ForestTree", "Axe" etc.)
    protected String entityType;
    // Boolean used to determine if the entity is obstructable in game
    protected boolean obstructed;

    public SaveableEntity() {
        super();
    }

    public SaveableEntity(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
    }

    public SaveableEntity(float col, float row, int renderOrder, String fixtureDef) {
        super(col, row, renderOrder, fixtureDef);
    }

    @Override
    public SaveableEntityMemento save() {
        return new SaveableEntityMemento(this);
    }

    /**
     * Gets the entity type of this entity
     *
     * @return the entity type of this entity
     */
    public String getEntityType() {
        return this.entityType;
    }

    @Override
    public void load(SaveableEntityMemento memento) {
        this.setEntityID(memento.entityID);
        setRenderOrder(memento.renderOrder);
        this.obstructed = memento.obstructed;
        /*
         * this.setBody(memento.body); this.setFixture(memento.fixture);
         */
        this.setCollidable(memento.isCollidable);
        this.setTexture(memento.texture);
        this.setColRenderLength(memento.colRenderLength);
        this.setRowRenderLength(memento.rowRenderLength);
        this.setPosition(memento.col, memento.row);
    }

    public class SaveableEntityMemento extends AbstractMemento {
        public String entityType;
        public int height;
        public float row;
        public float col;
        public int entityID;
        public float colRenderLength;
        public float rowRenderLength;
        public int renderOrder;
        public boolean obstructed;

        // TODO:dannathan find out if these need to be saved (they cause a stack
        // overflow in gson)
        /*
         * private Body body; private Fixture fixture;
         */

        protected Boolean isCollidable;
        protected String texture;

        public SaveableEntityMemento(SaveableEntity entity) {
            this.entityType = entity.entityType;
            this.height = entity.getHeight();
            this.row = entity.getRow();
            this.col = entity.getCol();
            this.entityID = entity.getEntityID();
            this.colRenderLength = entity.getColRenderLength();
            this.rowRenderLength = entity.getRowRenderLength();
            this.renderOrder = entity.getRenderOrder();
            this.obstructed = entity.obstructed;

            /*
             * this.body = entity.getBody(); this.fixture = entity.getFixture();
             */

            this.isCollidable = entity.getCollidable();
            this.texture = entity.getTexture();
        }
    }

}