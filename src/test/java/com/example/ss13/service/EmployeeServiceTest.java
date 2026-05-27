package com.example.ss13.service;

import com.example.ss13.exception.ResourceNotFoundException;
import com.example.ss13.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getAllEmployees_ReturnList() {
        List<Employee> list = employeeService.getAllEmployees();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(5, list.size());
    }

    @Test
    public void getById_Found() {
        Employee emp = employeeService.getEmployeeById(1L);
        assertNotNull(emp);
        assertEquals("Nguyen Van A", emp.getFullName());
    }

    @Test
    public void getById_NotFound_ThrowException() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(99L);
        });
        assertTrue(exception.getMessage().contains("Employee not found with id: 99"));
    }

    @Test
    public void addEmployee_Success() {
        Employee newEmp = new Employee(null, "Test User", "QA", 10000000.0);
        Employee created = employeeService.createEmployee(newEmp);
        assertNotNull(created.getId());
        assertEquals("Test User", created.getFullName());
        assertEquals(6, employeeService.getAllEmployees().size());
    }

    @Test
    public void deleteEmployee_RemovesCorrectElement() {
        int initialSize = employeeService.getAllEmployees().size();
        employeeService.deleteEmployee(2L);
        assertEquals(initialSize - 1, employeeService.getAllEmployees().size());
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(2L);
        });
    }
}
