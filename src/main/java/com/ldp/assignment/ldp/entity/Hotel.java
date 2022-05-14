package com.ldp.assignment.ldp.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="hotel_name")
    private String hotelName;

    @Column(name="price")
    private int price;

    @Column(name = "location")
    private String location;

    @OneToOne(mappedBy = "hotel",
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private Manager manager;

    @OneToMany(mappedBy = "hotels", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Customer> customersList;

    public Hotel(String hotelName, int price, String location) {
        this.hotelName = hotelName;
        this.price = price;
        this.location = location;
    }

    public Hotel() {

    }

    public List<Customer> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", hotelName='" + hotelName + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                '}';
    }
}
