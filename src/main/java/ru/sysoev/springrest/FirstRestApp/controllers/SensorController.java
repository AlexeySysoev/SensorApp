package ru.sysoev.springrest.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sysoev.springrest.FirstRestApp.dto.SensorDTO;
import ru.sysoev.springrest.FirstRestApp.services.SensorService;
import ru.sysoev.springrest.FirstRestApp.util.DTO.DTOConverter;
import ru.sysoev.springrest.FirstRestApp.util.sensor.SensorValidator;
import ru.sysoev.springrest.FirstRestApp.util.sensor.SensorErrorResponse;
import ru.sysoev.springrest.FirstRestApp.util.sensor.SensorNotCreatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final DTOConverter dtoConverter;
    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, DTOConverter dtoConverter) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping
    public List<SensorDTO> getAllSensors() {
        return sensorService.findAll().stream().map(dtoConverter::convertToSensorDTO).collect(Collectors.toList());
    }
    @PostMapping("/registration")
    //регистрация сенсора
    public ResponseEntity<HttpStatus> sensorRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                        BindingResult bindingResult) {
        sensorValidator.validate(dtoConverter.convertToSensor(sensorDTO), bindingResult);
        if (bindingResult.hasErrors()) {
           StringBuilder errorMsg = new StringBuilder();
           List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach((error) -> {errorMsg.append(error.getField()).append(" - ")
                    .append(error.getDefaultMessage()).append(";");});
            throw new SensorNotCreatedException(errorMsg.toString());

        }
        sensorService.save(dtoConverter.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
