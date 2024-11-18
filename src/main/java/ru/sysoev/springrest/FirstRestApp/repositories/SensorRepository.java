package ru.sysoev.springrest.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findSensorByName(String sensorName);
}
