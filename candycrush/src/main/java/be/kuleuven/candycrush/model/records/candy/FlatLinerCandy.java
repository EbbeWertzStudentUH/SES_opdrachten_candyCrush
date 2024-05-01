package be.kuleuven.candycrush.model.records.candy;

import be.kuleuven.candycrush.model.interfaces.Candy;

/*
    Wanneer je een rij van minstens 3 snoepjes maakt,
    wordt ook de volledige onderste rij verwijderd.
 */
public record FlatLinerCandy() implements Candy {
}
