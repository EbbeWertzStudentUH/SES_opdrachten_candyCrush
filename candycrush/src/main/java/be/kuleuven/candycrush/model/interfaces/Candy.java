package be.kuleuven.candycrush.model.interfaces;

import be.kuleuven.candycrush.model.records.candy.*;

public sealed interface Candy permits ChocolateGoldCandy, EmptyCandy, FlatLinerCandy, HyperDextroseCandy, NormalCandy, Tri_nitrotolueneCandy {

}
