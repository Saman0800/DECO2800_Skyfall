package deco2800.skyfall.util;

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
        return (float) Math.sqrt((point.getCol() - getCol()) * (point.getCol() - getCol()) +
                                         (point.getRow() - getRow()) * (point.getRow() - getRow()));
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
     * Equals Method returns true iff the two objects are equal based on their col
     * and row values.
     */
    @Override
    public boolean equals(Object obj) {
        // FIXME This isn't technically transitive (one of the properties required by the spec), since the values just
        //  have to be "close enough".
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
        return String.format("%f, %f", getCol(), getRow());
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
        return (float)Math.sqrt(getRow() * getRow() + getCol() * getCol());
    }

    public HexVector normalized() {
        float len = length();
        HexVector temp = new HexVector(getCol() / len, getRow() / len);
        return temp;
    }

    public double getAngle() {
        HexVector temp = this.normalized();
        return angle;
    }
}
