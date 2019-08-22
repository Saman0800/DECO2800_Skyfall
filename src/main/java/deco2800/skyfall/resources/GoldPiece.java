package deco2800.skyfall.resources;

public class GoldPiece {
    public int value;

    public GoldPiece(int value){
        if (value == 5 || value == 10 || value == 50 || value == 100){
            this.value = value;
        } System.out.println("This is not a valid gold piece");

    }
}
