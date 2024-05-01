package be.kuleuven.candycrush.model.interfaces;

import be.kuleuven.candycrush.model.records.candy.*;

public sealed interface Candy permits NormalCandy, Tri_nitrotolueneCandy, FlatLinerCandy, ChocolateGoldCandy, HyperDextroseCandy {

}
