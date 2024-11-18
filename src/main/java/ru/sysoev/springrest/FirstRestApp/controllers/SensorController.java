package ru.sysoev.springrest.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sysoev.springrest.FirstRestApp.dto.SensorDTO;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.services.SensorService;
import ru.sysoev.springrest.FirstRestApp.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;
    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getAllSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }
    @PostMapping("/registration")
    //регистрация сенсора
    public ResponseEntity<HttpStatus> sensorRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                        BindingResult bindingResult) {
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);
        if (bindingResult.hasErrors()) {
           throw new RuntimeException("Name is not valid or already exist");
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
