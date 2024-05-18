package be.kuleuven.candycrush.model.records;

public record PosPair(Position p1, Position p2) {

    @Override
    public String toString() {
        if(p1.row() == p2.row()){
            return String.format("(R%d: C%d-%d)", p1.row(), p1.col(), p2.col());
        } else if(p1.col() == p2.col()){
            return String.format("(C%d: R%d-%d)", p1.col(), p1.row(), p2.row());
        } else return "invalid";
    }

}
