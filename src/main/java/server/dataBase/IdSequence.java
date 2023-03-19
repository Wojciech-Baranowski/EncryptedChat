package server.dataBase;

public class IdSequence {

    private int sequence;

    public IdSequence(int maxId) {
        this.sequence = maxId;
    }

    public int getNextId() {
        sequence++;
        return sequence;
    }

}
