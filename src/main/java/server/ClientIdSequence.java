package server;

public class ClientIdSequence {

    private int sequence;

    public ClientIdSequence() {
        this.sequence = 0;
    }

    public int getNextId() {
        sequence++;
        return sequence;
    }

}
