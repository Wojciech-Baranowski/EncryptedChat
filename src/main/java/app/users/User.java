package app.users;

import lombok.Getter;

@Getter
public class User {

    private final int id;

    public User(int id) {
        this.id = id;
    }

}
