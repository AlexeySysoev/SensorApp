package ru.sysoev.springrest.FirstRestApp.util.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.services.SensorService;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;
    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        System.out.println("in validator: " + sensor.getName());
        Optional<Sensor> dbSensor = Optional.ofNullable(sensorService.findOne(sensor.getName()));
        if (dbSensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor already defined");
        }
        if (sensor.getName().isEmpty()) {
            errors.rejectValue("name", "", "Name should not be empty");
        }
        if (sensor.getName().isBlank()) {
            errors.rejectValue("name", "", "Name should not contain only space character");
        }
    }
    public boolean checkSensor (Object target) {
        Sensor sensor = (Sensor) target;
        Optional<Sensor> dbSensor = Optional.ofNullable(sensorService.findOne(sensor.getName()));
        return dbSensor.isPresent();
    }
    public Sensor checkSensorId (Object target) {
        Sensor sensor = (Sensor) target;
        Optional<Sensor> dbSensor = Optional.ofNullable(sensorService.findOne(sensor.getName()));
        return dbSensor.orElse(null);
    }
}
