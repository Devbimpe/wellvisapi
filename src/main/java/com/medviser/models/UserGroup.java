package com.medviser.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Longbridge on 13/06/2018.
 */
@Entity
public class UserGroup extends CommonFields {
    public String name;

    @ManyToMany
    public List<User> users;

    public UserGroup() {
    }

    public UserGroup(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
