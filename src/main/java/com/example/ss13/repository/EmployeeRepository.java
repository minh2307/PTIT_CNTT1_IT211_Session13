package com.example.ss13.repository;

import com.example.ss13.model.Entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees=  new ArrayList<>();

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }
    public Optional<Employee> findById(Long id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst();
    }
    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }
    public Employee update(Employee employee) {
        deleteById(employee.getId());
        employees.add(employee);
        return employee;
    }

    public void deleteById(Long id) {
        employees.removeIf(e -> e.getId().equals(id));
    }

    public boolean existsById(Long id) {
        return employees.stream().anyMatch(e -> e.getId().equals(id));
    }
}
