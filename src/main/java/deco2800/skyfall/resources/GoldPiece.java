package deco2800.skyfall.resources;

/**
 * The civilisation's currency is Gold Pieces valued as either 5G,
 * 10G, 50G or 100G
 */
public class GoldPiece {

    // the value of the piece of gold (either 5G,10G,50G or 100G)
    public int value;

    /**
     * Creates a gold piece with a particular value.
     * @param value The value of the piece of gold, either 5G, 10G, 50G or 100G.
     */
    public GoldPiece(int value){
        if (value == 5 || value == 10 || value == 50 || value == 100){
            this.value = value;
        } else {
            System.out.println("Invalid piece of gold");
        }



    }
    /**
     *
     */
    public Integer getValue(GoldPiece g){
        return this.value;
    }
}
