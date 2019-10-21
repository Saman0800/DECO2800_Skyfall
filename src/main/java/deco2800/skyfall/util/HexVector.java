package deco2800.skyfall.util;
import com.badlogic.gdx.math.Vector2;

public class HexVector {
    private float col;
    private float row;
    private double angle;

    /**
     * Constructor for a HexVector
     * 
     * @param col the col value for the vector
     * @param row the row value for the vector
     */
    public HexVector(float col, float row) {
        this.col = col;
        this.row = row;
    }

    public HexVector(HexVector vector) {
        this.col = vector.col;
        this.row = vector.row;
    }

    public HexVector(String vector) {
        String[] pos = vector.split(",", 2);
        this.col = Float.parseFloat(pos[0]);
        this.row = Float.parseFloat(pos[1]);
    }

    public HexVector() {

    }

    /**
     * Getter for the col value of the vector
     * 
     * @return the col value
     */
    public float getCol() {
        return col;
    }

    /**
     * Getter for the row value of the vector
     * 
     * @return the row value
     */
    public float getRow() {
        return row;
    }

    /**
     * Setter for the column value
     * 
     * @param col the column value to be set
     */
    public void setCol(float col) {
        this.col = col;
    }

    /**
     * Setter for the row value
     * 
     * @param row the row value to be set
     */
    public void setRow(float row) {
        this.row = row;
    }

    /**
     * Setter for the column and the row value at the same time.
     *
     * @param col the column value to be set
     * @param row the row value to be set
     */
    public void set(float col, float row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Setter for the column and the row value at the same time.
     *
     * @param vector the vector containing th new column and row value
     */
    public void set(HexVector vector) {
        set(vector.getCol(), vector.getRow());
    }

    /**
     * Calculates the distance between two coordinates on a 2D plane. Based off of
     * the cubeDistance function.
     * 
     * @param vcol the x coordinate
     * @param vrow the y coordinate
     * @return the distance between the two coordinates
     */
    public float distance(float vcol, float vrow) {
        Cube thisVector = Cube.oddqToCube(getCol(), getRow());
        Cube otherVector = Cube.oddqToCube(vcol, vrow);

        return Cube.cubeDistance(thisVector, otherVector);
    }

    public float distance(HexVector vector) {
        return distance(vector.getCol(), vector.getRow());
    }

    private float distanceAsCartesian(HexVector point) {
        return (float) Math.sqrt((point.getCol() - getCol()) * (point.getCol() - getCol())
                + (point.getRow() - getRow()) * (point.getRow() - getRow()));
    }

    public void moveToward(HexVector point, double distance) {
        if (distanceAsCartesian(point) < distance) {
            setCol(point.getCol());
            setRow(point.getRow());
            return;
        }

        double deltaCol = getCol() - point.getCol();
        double deltaRow = getRow() - point.getRow();

        angle = Math.atan2(deltaRow, deltaCol) + Math.PI;

        double xShift = Math.cos(angle) * distance;
        double yShift = Math.sin(angle) * distance;

        set((float) (getCol() + xShift), (float) (getRow() + yShift));
    }

    public boolean isCloseEnoughToBeTheSame(HexVector vector) {
        return MathUtil.floatEquality(this.getCol(), vector.getCol())
                && MathUtil.floatEquality(this.getRow(), vector.getRow());
    }

    public boolean isCloseEnoughToBeTheSameByDistance(HexVector vector, float e) {
        return MathUtil.floatEquality(this.getCol(), vector.getCol(), e)
                && MathUtil.floatEquality(this.getRow(), vector.getRow(), e);
    }

    /**
     * Calculates a rotated version of the vector with the angle specified
     *
     * @param angle The angle to rotate in degrees
     * @return the rotated hexvector
     */
    public HexVector rotated(float angle) {
        float radAng = (float)Math.toRadians(angle);

        HexVector temp = new HexVector();

        temp.col = (float)(this.col * Math.cos(radAng) - this.row * Math.sin(radAng));
        temp.row = (float)(this.col * Math.sin(radAng) + this.row * Math.cos(radAng));

        return temp;
    }

    /**
     * Calculates a multiplied version of the vector with the value specified.
     *
     * @param val The value to multiply by
     * @return the scaled hexvector
     */
    public HexVector times(float val) {
        HexVector temp = new HexVector();

        temp.col = this.col * val;
        temp.col = this.row * val;

        return temp;
    }

    /**
     * Returns the identical Vector2 version of this HexVector.
     *
     * @return the Vector2 version of this HexVector
     */
    public Vector2 toVector2() {
        return new Vector2(col, row);
    }

    /**
     * Equals Method returns true iff the two objects are equal based on their col
     * and row values.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HexVector)) {
            return false;
        }
        HexVector vector = (HexVector) obj;
        return isCloseEnoughToBeTheSame(vector);
    }

    @Override
    public int hashCode() {
        return ((31 * (int) this.getCol()) + 17) * (int) this.getRow();
    }

    @Override
    public String toString() {
        return String.format("%.1f, %.1f", getCol(), getRow());
    }

    public HexVector getInt() {
        return new HexVector(Math.round(getCol()), Math.round(getRow()));
    }

    public HexVector add(HexVector add) {
        float newRow = getRow() + add.getRow();
        float newCol = getCol() + add.getCol();
        return new HexVector(newCol, newRow);
    }

    public HexVector subtract(HexVector subtract) {
        float newRow = getRow() - subtract.getRow();
        float newCol = getCol() - subtract.getCol();
        return new HexVector(newCol, newRow);
    }

    public float length() {
        return (float) Math.sqrt(getRow() * getRow() + getCol() * getCol());
    }

    public HexVector normalized() {
        float len = length();
        return new HexVector(getCol() / len, getRow() / len);
    }

    public double getAngle() {
        return this.angle;
    }
}
