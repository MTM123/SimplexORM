package me.nickac.simplexorm;

import javax.persistence.Column;
import javax.persistence.Id;

public class DatabaseObject {
    @Id
    @Column()
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
