package ru.sysoev.springrest.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sysoev.springrest.FirstRestApp.models.Measurements;

import java.util.List;

public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
    List<Measurements> findMeasurementsByRainingIsTrue();
    List<Measurements> findMeasurementsBySensor_Id(int sensor_id);
}
