package com.ldp.assignment.ldp.entity;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private Customer customer;

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private Manager manager;


    public User(String userName, String password, int enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {

    }


    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
