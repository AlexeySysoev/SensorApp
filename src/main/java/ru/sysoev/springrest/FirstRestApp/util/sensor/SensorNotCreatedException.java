package ru.sysoev.springrest.FirstRestApp.util.sensor;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}
