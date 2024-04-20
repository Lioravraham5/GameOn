package com.example.gameon.Models;

public class Address {

    private String country;
    private String city;
    private String street;
    private double longitude = 0.0;
    private double latitude = 0.0;

    public Address() {

    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Address setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Address setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    @Override
    public String toString() {
        return country + ", " +
                city + ", " +
                street;
    }
}
