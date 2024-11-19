package ru.sysoev.springrest.FirstRestApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MeasurementsDTO {
    @NotEmpty
    @Size(min = -100, max = 100, message = "value of measure must be from -100 to 100 degrees")
    private double value;
    @NotEmpty
    private boolean raining;

    private SensorDTO sensor;
    public MeasurementsDTO(){};



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

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementsDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor.getName() +
                '}';
    }
}
