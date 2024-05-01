package be.kuleuven.candycrush.model.records.candy;

import be.kuleuven.candycrush.model.interfaces.Candy;

public record NormalCandy(int color) implements Candy {

    public NormalCandy{
        if(color < 0 || color > 3){
            throw new IllegalArgumentException("color: "+color+" must be in the range of 0-3");
        }
    }

}
