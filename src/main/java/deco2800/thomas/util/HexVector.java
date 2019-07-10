package deco2800.thomas.util;

public class HexVector {
	private float col;
    private float row;

    /**
     * Constructor for a HexVector
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

    public HexVector() {

    }

    /**
     * Getter for the col value of the vector
     * @return the col value
     */
    public float getCol() {
        return col;
    }

    /**
     * Getter for the row value of the vector
     * @return the row value
     */
    public float getRow() {
        return row;
    }

    /**
     * Setter for the column value
     * @param col the column value to be set
     */
    public void setCol(float col) {
        this.col = col;
    }

    /**
     * Setter for the row value
     * @param col the row value to be set
     */
    public void setRow(float col) {
        this.row = col;
    }
    
    /**
     * Calculates the distance between two coordinates on a 2D plane. Based off of the cubeDistance function.
     * @param vcol the x coordinate
     * @param vrow the y coordinate
     * @return the distance between the two coordinates
     */
    public float distance(float vcol, float vrow) {
        return distance(new HexVector(vcol, vrow));
    }
    
    
    public float distance(HexVector vector) {
    	Cube thisVector = Cube.oddqToCube(col, row);
    	Cube otherVector = Cube.oddqToCube(vector.col, vector.row);
    	
    	return Cube.cubeDistance(thisVector, otherVector);
    }
    
    private float distanceAsCartesian(HexVector point) {
        return (float) Math.sqrt(Math.pow(point.col - col, 2) + Math.pow(point.row - row, 2));
    }
    

    public void moveToward(HexVector point, double distance) {
    	//System.out.println(distance(point));
        if (distanceAsCartesian(point) < distance) {
            this.col = point.col;
            this.row = point.row;
            return;
        }
        
        double deltaCol = this.col - point.col;
        double deltaRow = this.row - point.row;
        double angle;

        angle = Math.atan2(deltaRow, deltaCol) + Math.PI;

        double xShift = Math.cos(angle) * distance;
        double yShift = Math.sin(angle) * distance;
        
        //System.out.println(String.format("    dCol: %.2f, dRow: %.2f, angle: %.2f, colShift: %.2f, rowShift: %.2f", deltaCol, deltaRow, angle, xShift, yShift));

        
        this.col += xShift;
        this.row += yShift;
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
     * Equals Method returns true iff the two objects are equal 
     * based on their col and row values.
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
    	return String.format("[%f, %f]", col, row);
    }

	public HexVector getInt() {
		return new HexVector(Math.round(col),Math.round(row));
	}

	public HexVector add(HexVector add) {
		float row = getRow() + add.getRow();
		float col = getCol() + add.getCol();
		return new HexVector(col,row);
	}

}
