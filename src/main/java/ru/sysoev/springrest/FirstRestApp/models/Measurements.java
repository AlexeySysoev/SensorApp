package ru.sysoev.springrest.FirstRestApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
@Entity
@Table(name = "measurements")
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty
    @Size(min = -100, max = 100, message = "value of measure must be from -100 to 100 degrees")
    @Column (name = "value_of_measure")
    private double value;
    @NotEmpty
    @Column (name = "is_raining")
    private boolean raining;
    //Поработать со связью один ко многим (посмотреть урок)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor owner;
    @Column(name = "time_of_measure")
    private LocalDateTime timeOfMeasure;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getOwner() {
        return owner;
    }

    public void setOwner(Sensor owner) {
        this.owner = owner;
    }

    public LocalDateTime getTimeOfMeasure() {
        return timeOfMeasure;
    }

    public void setTimeOfMeasure(LocalDateTime timeOfMeasure) {
        this.timeOfMeasure = timeOfMeasure;
    }

    @Override
    public String toString() {
        return "Measurements{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", owner=" + owner +
                ", timeOfMeasure=" + timeOfMeasure +
                '}';
    }
}
