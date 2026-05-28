package com.example.ss13;

import com.example.ss13.model.Entity.Employee;
import com.example.ss13.repository.EmployeeRepository;
import com.example.ss13.service.EmployeeService;
import com.example.ss13.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl(new EmployeeRepository());

        employeeService.addEmployee(
                new Employee(null, "Nguyen Van A", "Engineering", 15000000)
        );

        employeeService.addEmployee(
                new Employee(null, "Tran Thi B", "HR", 12000000)
        );
    }

    @Test
    void getAllEmployees_ReturnList() {
        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertEquals(2, employees.size());
    }

    @Test
    void getById_Found() {
        Employee employee = employeeService.getEmployeeById(1L);

        assertNotNull(employee);
        assertEquals("Nguyen Van A", employee.getFullName());
    }

    @Test
    void getById_NotFound_ThrowException() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeById(100L)
        );

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void addEmployee_Success() {
        Employee savedEmployee = employeeService.addEmployee(
                new Employee(null, "Le Van C", "Finance", 13000000)
        );

        assertNotNull(savedEmployee);
        assertEquals(3L, savedEmployee.getId());
        assertEquals("Le Van C", savedEmployee.getFullName());
    }

    @Test
    void updateEmployee_Success() {
        Employee updated = employeeService.updateEmployee(
                1L,
                new Employee(null, "Nguyen Van A Updated", "Engineering", 18000000)
        );

        assertEquals(1L, updated.getId());
        assertEquals("Nguyen Van A Updated", updated.getFullName());
        assertEquals(18000000, updated.getSalary());
    }

    @Test
    void deleteEmployee_RemovesCorrectElement() {
        employeeService.deleteEmployee(1L);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(1, employees.size());
        assertThrows(RuntimeException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void addEmployee_InvalidSalary_ThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.addEmployee(new Employee(null, "Invalid", "QA", 0))
        );

        assertEquals("Salary must be > 0", exception.getMessage());
    }
}
