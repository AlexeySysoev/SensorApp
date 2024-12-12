package ru.sysoev.springrest.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sysoev.springrest.FirstRestApp.models.Measurements;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.repositories.MeasurementsRepository;
import ru.sysoev.springrest.FirstRestApp.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorRepository sensorRepository;
    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorRepository sensorRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Measurements measurements){
        enrichMeasurements(measurements);
        measurementsRepository.save(measurements);
    }
    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }
    public List<Measurements> findRainyDays() {
        return measurementsRepository.findMeasurementsByRainingIsTrue();
    }

    public List<Measurements> findBySensorId(int sensorId) {
        return measurementsRepository.findMeasurementsBySensor_Id(sensorId);
    }
    private void enrichMeasurements(Measurements measurements) {

        measurements.setTimeOfMeasure(LocalDateTime.now());

    }
}
