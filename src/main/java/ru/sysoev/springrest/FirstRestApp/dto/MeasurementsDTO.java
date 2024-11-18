package ru.sysoev.springrest.FirstRestApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;

public class MeasurementsDTO {
    @NotEmpty
    @Size(min = -100, max = 100, message = "value of measure must be from -100 to 100 degrees")
    private double value;
    @NotEmpty
    private boolean raining;
    private SensorDTO sensorDTO;

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

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }

    @Override
    public String toString() {
        return "MeasurementsDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensorDTO +
                '}';
    }
}
