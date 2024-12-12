package ru.sysoev.springrest.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sysoev.springrest.FirstRestApp.dto.MeasurementsDTO;
import ru.sysoev.springrest.FirstRestApp.dto.SensorDTO;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.services.MeasurementsService;
import ru.sysoev.springrest.FirstRestApp.util.DTO.DTOConverter;
import ru.sysoev.springrest.FirstRestApp.util.sensor.SensorErrorResponse;
import ru.sysoev.springrest.FirstRestApp.util.sensor.SensorNotFoundException;
import ru.sysoev.springrest.FirstRestApp.util.sensor.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorValidator sensorValidator;
    private final DTOConverter dtoConverter;
    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, SensorValidator sensorValidator, DTOConverter dtoConverter) {
        this.measurementsService = measurementsService;
        this.sensorValidator = sensorValidator;
        this.dtoConverter = dtoConverter;
    }

    @PostMapping("/add")
    //Добавляет новое измерение
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementsDTO measurementsDTO) {
        if (sensorValidator.checkSensor(dtoConverter.convertToSensor(measurementsDTO.getSensor()))){
            measurementsService.save(dtoConverter.convertToMeasurements(measurementsDTO));
        } else {
            throw new SensorNotFoundException(String.format("Sensor '%s' not exist", measurementsDTO.getSensor().getName()));
        }
    return ResponseEntity.ok(HttpStatus.OK);
}
    @GetMapping()
    //отображает все измерения
    public List<MeasurementsDTO> getAllMeasurements() {
    return measurementsService.findAll().stream().map(dtoConverter::convertToMeasurementsDTO).collect(Collectors.toList());
    }
    @GetMapping("/bySensor")
    public List<MeasurementsDTO> getMeasurementsBySensor(@RequestBody @Valid SensorDTO sensorDTO) {
        Sensor sensor = sensorValidator.checkSensorId(dtoConverter.convertToSensor(sensorDTO));
        return measurementsService.findBySensorId(sensor.getId()).stream()
                .map(dtoConverter::convertToMeasurementsDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    //отображает количество дождливых дней
    public List<MeasurementsDTO> getRainyDays() {
    return measurementsService.findRainyDays().stream().map(dtoConverter::convertToMeasurementsDTO).collect(Collectors.toList());
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
