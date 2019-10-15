package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.saving.RunTimeLoadException;

public class InvalidBiomeException extends RuntimeException {
    public InvalidBiomeException(String s){
        super(s);
    }
}
