package com.ldp.assignment.ldp.entity;

import javax.persistence.*;

@Entity
@Table(name="authorities")
public class Authorities {
    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "authority")
    private String authority;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "userName='" + userName + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
