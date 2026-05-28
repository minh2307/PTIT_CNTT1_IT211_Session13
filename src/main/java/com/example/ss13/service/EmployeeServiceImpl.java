package com.example.ss13.service;

import com.example.ss13.model.Entity.Employee;
import com.example.ss13.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found with id: {}", id);
                    return new RuntimeException("Employee not found");
                });
    }

    @Override
    public Employee addEmployee(Employee employee) {
        validateEmployee(employee);
        if (employee.getId() != null && repository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Employee with id " + employee.getId() + " already exists");
        }
        Employee saved = repository.save(employee);
        log.info("Added new employee: {}", saved);
        return saved;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        if (!repository.existsById(id)) {
            log.warn("Update failed - employee not found with id: {}", id);
            throw new RuntimeException("Employee not found");
        }
        validateEmployee(employee);
        employee.setId(id);
        Employee updated = repository.update(employee);
        log.info("Updated employee: {}", updated);
        return updated;
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            log.warn("Delete failed - employee not found with id: {}", id);
            throw new RuntimeException("Employee not found");
        }
        repository.deleteById(id);
        log.info("Deleted employee with id: {}", id);
    }

    private void validateEmployee(Employee e) {
        if (e.getFullName() == null || e.getFullName().isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        if (e.getDepartment() == null || e.getDepartment().isBlank()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        if (e.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be > 0");
        }
    }
}
