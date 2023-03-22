package server.userDataBase;

public class UserIdSequence {

    private long sequence;

    public UserIdSequence(long maxId) {
        this.sequence = maxId;
    }

    public long getNextId() {
        sequence++;
        return sequence;
    }

}
