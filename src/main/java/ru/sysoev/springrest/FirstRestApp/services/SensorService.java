package ru.sysoev.springrest.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findOne (String sensorName) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorName);
        return sensorRepository.findSensorByName(sensor.getName());
    }
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    @Transactional
    public void save(Sensor sensor) {
        enrichSensor(sensor);
        sensorRepository.save(sensor);
    }
    public void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
    }
}
