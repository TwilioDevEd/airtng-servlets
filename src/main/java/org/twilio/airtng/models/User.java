package org.twilio.airtng.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy="user")
    private List<Reservation> reservations;

    @OneToMany(mappedBy="user")
    private List<VacationProperty> vacationProperties;

    public User() {
        this.reservations = new ArrayList<>();
        this.vacationProperties = new ArrayList<>();
    }

    public User(
            String name,
            String email,
            String password,
            String countryCode,
            String phoneNumber) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addReservation(Reservation reservation) {

        if (this.reservations.add(reservation) && reservation.getUser() != this) {
            reservation.setUser(this);
        }
    }

    public void removeReservation(Reservation reservation) {
        if (this.reservations.remove(reservation) && reservation.getUser() == this) {
            reservation.setUser(null);
        }
    }

    public void addVacationProperty(VacationProperty vacationProperty) {

        if (this.vacationProperties.add(vacationProperty) && vacationProperty.getUser() != this) {
            vacationProperty.setUser(this);
        }
    }

    public void removeVacationProperty(VacationProperty vacationProperty) {
        if (this.reservations.remove(vacationProperty) && vacationProperty.getUser() == this) {
            vacationProperty.setUser(null);
        }
    }

}