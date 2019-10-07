package deco2800.skyfall.entities;

import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.Saveable;
import java.io.Serializable;

public abstract class SaveableEntity extends AbstractEntity
        implements Saveable<SaveableEntity.SaveableEntityMemento>, Serializable {

    // The type of entity this is (e.g. "ForestTree", "Axe" etc.)
    protected String entityType;
    // boolean used to determine if the entity is obstructable in game
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
     * A simple getter function to retrieve the obstruction value of this object
     *
     * @return The obstruction value.
     */
    public boolean isObstructed() {
        return this.obstructed;
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

    public static class SaveableEntityMemento extends AbstractMemento implements Serializable {
        private String entityType;
        private int height;
        private float row;
        private float col;
        private int entityID;
        private float colRenderLength;
        private float rowRenderLength;
        private int renderOrder;
        private boolean obstructed;

        // TODO:dannathan find out if these need to be saved (they cause a stack
        // overflow in gson)
        /*
         * private Body body; private Fixture fixture;
         */

        protected boolean isCollidable;
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

        /**
         * @return Returns the entity type for this memento object.
         */
        public String getEntityType() {
            return this.entityType;
        }

        /**
         * @return Returns the height value for this memento object.
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * @return Returns the row value for this memento object.
         */
        public float getRow() {
            return this.row;
        }

        /**
         * @return Returns the column value for this memento object.
         */
        public float getCol() {
            return this.col;
        }

        /**
         * @return Returns the render order for this memento object.
         */
        public int getRenderOrder() {
            return this.renderOrder;
        }

        /**
         * @return Returns the for this memento object.
         */
        public int getEntityID() {
            return this.entityID;
        }

        /**
         * @return Returns the column render value for this memento object.
         */
        public float getColRenderLength() {
            return this.colRenderLength;
        }

        /**
         * @return Returns the row render value for this memento object.
         */
        public float getRowRenderLength() {
            return this.rowRenderLength;
        }

        /**
         * @return Returns true if the memento object is obstructable.
         */
        public boolean getObstructed() {
            return this.obstructed;
        }
    }

}