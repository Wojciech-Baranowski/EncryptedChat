package server.dataBase;

public class IdSequence {

    private long sequence;

    public IdSequence(long maxId) {
        this.sequence = maxId;
    }

    public long getNextId() {
        sequence++;
        return sequence;
    }

}
