package com.example.springbootweb.services;

import com.example.springbootweb.dto.EmployeeDTO;
import com.example.springbootweb.entities.EmployeeEntity;
import com.example.springbootweb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployee(Long Id)
    {
        EmployeeEntity er = employeeRepository.findById(Id).orElse(null);
        return modelMapper.map(er, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getEmployees()
    {
        List<EmployeeEntity> er = employeeRepository.findAll();

        return er.stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .toList();
    }

    public EmployeeDTO postEmployeesJson(EmployeeDTO input) {
        EmployeeEntity inputNew = modelMapper.map(input, EmployeeEntity.class);
        EmployeeEntity er = employeeRepository.save(inputNew);
        return modelMapper.map(er, EmployeeDTO.class);
    }

    public Boolean deleteEmployee(Long employeeId) {
        EmployeeEntity e = employeeRepository.findById(employeeId).orElse(null);
        if(e == null) return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updateEmployeeAll(EmployeeDTO input, Long employeeId) {
        input.setId(employeeId);
        EmployeeEntity e = modelMapper.map(input, EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(e), EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Map<String, Object> input, Long employeeId) {
        EmployeeEntity e = employeeRepository.findById(employeeId).orElse(null);
        if(e == null) return null;
        input.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(EmployeeEntity.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, e, value);
        });
        EmployeeEntity result = employeeRepository.save(e);
        return modelMapper.map(result, EmployeeDTO.class);
    }
}
