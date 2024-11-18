package ru.sysoev.springrest.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;
import ru.sysoev.springrest.FirstRestApp.dto.MeasurementsDTO;
import ru.sysoev.springrest.FirstRestApp.dto.SensorDTO;
import ru.sysoev.springrest.FirstRestApp.models.Measurements;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.services.MeasurementsService;
import ru.sysoev.springrest.FirstRestApp.services.SensorService;
import ru.sysoev.springrest.FirstRestApp.util.SensorValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final SensorService sensorService;
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;
    @Autowired
    public MeasurementsController(SensorService sensorService, MeasurementsService measurementsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/add")
    //Добавляет новое измерение
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementsDTO measurementsDTO){
        if (sensorValidator.checkSensor(measurementsDTO.getSensorDTO())){
            measurementsService.save(convertToMeasurements(measurementsDTO));
        } else {
            System.out.println("Sensor not found");
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

    return ResponseEntity.ok(HttpStatus.OK);
}
    @GetMapping()
    //отображает все измерения
    public List<MeasurementsDTO> getAllMeasurements() {
    return measurementsService.findAll().stream().map(this::convertToMeasurementsDTO).collect(Collectors.toList());
    }
    @GetMapping("rainyDaysCount")
    //отображает количество дождливых дней
    public List<MeasurementsDTO> getRainyDays() {
    return measurementsService.findRainyDays().stream().map(this::convertToMeasurementsDTO).collect(Collectors.toList());
    }

    private Measurements convertToMeasurements(MeasurementsDTO measurementsDTO) {
        Measurements measurements = modelMapper.map(measurementsDTO, Measurements.class);
        Sensor owner = new Sensor();
        owner.setName(measurementsDTO.getSensorDTO().getName());
        owner.setCreatedAt(LocalDateTime.now());
        owner.setId(sensorService.findOne(measurementsDTO.getSensorDTO().getName()).getId());
        measurements.setOwner(owner);
        return measurements;
    }
    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurements) {
        MeasurementsDTO measurementsDTO = modelMapper.map(measurements, MeasurementsDTO.class);
        SensorDTO sensor = new SensorDTO();
        sensor.setName(measurements.getOwner().getName());
        measurementsDTO.setSensorDTO(sensor);
        return measurementsDTO;
    }
}
