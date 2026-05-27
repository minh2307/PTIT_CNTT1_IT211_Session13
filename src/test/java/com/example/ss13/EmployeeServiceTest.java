package com.example.ss13;

import com.example.employeemanagement.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();

        employeeService.addEmployee(
                new Employee(null, "Nguyen Van A", "Engineering", 15000000)
        );

        employeeService.addEmployee(
                new Employee(null, "Tran Thi B", "HR", 12000000)
        );
    }

    // Test case 1
    @Test
    void getAllEmployees_ReturnList() {

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertEquals(2, employees.size());
    }

    // Test case 2
    @Test
    void getById_Found() {

        Employee employee = employeeService.getEmployeeById(1L);

        assertNotNull(employee);
        assertEquals("Nguyen Van A", employee.getFullName());
    }

    // Test case 3
    @Test
    void getById_NotFound_ThrowException() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeById(100L)
        );

        assertEquals("Employee not found", exception.getMessage());
    }

    // Test case 4
    @Test
    void addEmployee_Success() {

        Employee newEmployee = new Employee(
                null,
                "Le Van C",
                "Finance",
                13000000
        );

        Employee savedEmployee = employeeService.addEmployee(newEmployee);

        assertNotNull(savedEmployee);
        assertEquals(3L, savedEmployee.getId());
        assertEquals("Le Van C", savedEmployee.getFullName());
    }

    // Test case 5
    @Test
    void deleteEmployee_RemovesCorrectElement() {

        employeeService.deleteEmployee(1L);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(1, employees.size());

        assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeById(1L)
        );
    }
}