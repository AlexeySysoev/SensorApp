package ru.sysoev.springrest.FirstRestApp.util.sensor;

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(String msg) {
        super(msg);
    }
}
