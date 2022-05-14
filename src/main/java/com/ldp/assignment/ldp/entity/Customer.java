package com.ldp.assignment.ldp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(name="date_booked")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date date;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="hotel_booked")
    private Hotel hotels;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userkey")
    private User user;

    public Customer(){}

    public Customer(String firstName, String lastName, String email, Date date, Hotel hotels, User user) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.date = date;
        this.hotels = hotels;
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotels() {
        return hotels;
    }

    public void setHotels(Hotel hotels) {
        this.hotels = hotels;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", date=" + date +
                ", hotels=" + hotels +
                ", user=" + user +
                '}';
    }
}
