package ru.sysoev.springrest.FirstRestApp.util.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sysoev.springrest.FirstRestApp.dto.MeasurementsDTO;
import ru.sysoev.springrest.FirstRestApp.dto.SensorDTO;
import ru.sysoev.springrest.FirstRestApp.models.Measurements;
import ru.sysoev.springrest.FirstRestApp.models.Sensor;
import ru.sysoev.springrest.FirstRestApp.services.SensorService;

import java.time.LocalDateTime;

@Component
public class DTOConverter {
    private ModelMapper modelMapper;
    private SensorService sensorService;
    @Autowired
    public DTOConverter(ModelMapper modelMapper, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
    public SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
    public Measurements convertToMeasurements(MeasurementsDTO measurementsDTO) {
//        System.out.println("m: " + measurementsDTO);
//        System.out.println("s: " + measurementsDTO.getSensor());
//        Sensor sensor = new Sensor();
//        sensor.setName(measurementsDTO.getSensor().getName());
//        Measurements measurements = new Measurements();
//        measurements.setSensor(sensor);
//        measurements = modelMapper.map(measurementsDTO, Measurements.class);

        Measurements measurements = modelMapper.map(measurementsDTO, Measurements.class);
        Sensor owner = new Sensor();
        owner.setName(measurementsDTO.getSensor().getName());
        System.out.println("sensorName DTO: " + measurementsDTO.getSensor().getName());
        owner.setCreatedAt(LocalDateTime.now());
        owner.setId(sensorService.findOne(measurementsDTO.getSensor().getName()).getId());
        measurements.setSensor(owner);
        return measurements;
    }
    public MeasurementsDTO convertToMeasurementsDTO(Measurements measurements) {
        MeasurementsDTO measurementsDTO = modelMapper.map(measurements, MeasurementsDTO.class);
        SensorDTO sensor = new SensorDTO();
        sensor.setName(measurements.getSensor().getName());
        measurementsDTO.setSensor(sensor);
        return measurementsDTO;
    }
}
