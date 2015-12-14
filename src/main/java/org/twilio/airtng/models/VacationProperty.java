package org.twilio.airtng.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vacation_properties")
public class VacationProperty {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "vacationProperty")
    private List<Reservation> reservations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public VacationProperty() {
        this.reservations = new ArrayList<Reservation>();
    }

    public VacationProperty(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addReservation(Reservation reservation) {

        if (this.reservations.add(reservation) && reservation.getVacationProperty() != this) {
            reservation.setVacationProperty(this);
        }
    }

    public void removeReservation(Reservation reservation) {
        if (this.reservations.remove(reservation) && reservation.getVacationProperty() == this) {
            reservation.setVacationProperty(null);
        }
    }
}
