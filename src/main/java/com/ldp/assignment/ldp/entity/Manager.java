package com.ldp.assignment.ldp.entity;

import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager extends BaseEntity {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_managed")
    private Hotel hotel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userkey")
    private User user;

    public Manager(){

    }

    public Manager(String firstName, String lastName, String email, Hotel hotel) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", hotel=" + hotel +
                '}';
    }

}
