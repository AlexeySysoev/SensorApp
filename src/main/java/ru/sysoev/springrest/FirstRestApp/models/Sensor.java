package ru.sysoev.springrest.FirstRestApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size (min = 3, max = 30, message = "Name of sensor from3 to 30 characters")
    @Column(name = "name")
    private String name;
    @Column (name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Measurements> measurementsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Measurements> getMeasurementsList() {
        return measurementsList;
    }

    public void setMeasurementsList(List<Measurements> measurementsList) {
        this.measurementsList = measurementsList;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", measurementsList=" + measurementsList +
                '}';
    }
}
