package org.twilio.airtng.models;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacation_property_id")
    private VacationProperty vacationProperty;

    public Reservation() {
    }

    public Reservation(String message, VacationProperty vacationProperty, User user) {
        this();
        this.message = message;
        this.vacationProperty = vacationProperty;
        this.user = user;
        this.setStatus(Status.Pending);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return Status.getType(this.status);
    }

    public void setStatus(Status status) {
        if (status == null) {
            this.status = null;
        } else {
            this.status = status.getValue();
        }
    }

    public VacationProperty getVacationProperty() {
        return vacationProperty;
    }

    public void setVacationProperty(VacationProperty vacationProperty) {
        this.vacationProperty = vacationProperty;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void reject() {
        this.setStatus(Status.Reject);
    }

    public void confirm() {
        this.setStatus(Status.Confirmed);
    }

    public enum Status {

        Pending(0), Confirmed(1), Reject(2);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status getType(Integer id) {

            if (id == null) {
                return null;
            }

            for (Status status : Status.values()) {
                if (id.equals(status.getValue())) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No matching type for value " + id);
        }
    }
}