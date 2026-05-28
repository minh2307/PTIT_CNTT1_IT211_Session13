package com.example.ss13;

import com.example.ss13.exception.EmployeeNotFoundException;
import com.example.ss13.model.Employee;
import com.example.ss13.repository.EmployeeRepository;
import com.example.ss13.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        emp1 = new Employee(1L, "Nguyen Van A", "Engineering", 15000000);
        emp2 = new Employee(2L, "Tran Thi B", "HR", 12000000);
    }

    // Test case 1
    @Test
    void getAllEmployees_ReturnList() {
        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(2, employees.size());
        verify(repository, times(1)).findAll();
    }

    // Test case 2
    @Test
    void getById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(emp1));

        Employee employee = employeeService.getEmployeeById(1L);

        assertNotNull(employee);
        assertEquals("Nguyen Van A", employee.getFullName());
        verify(repository, times(1)).findById(1L);
    }

    // Test case 3
    @Test
    void getById_NotFound_ThrowException() {
        when(repository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(100L)
        );
        verify(repository, times(1)).findById(100L);
    }

    // Test case 4
    @Test
    void addEmployee_Success() {
        Employee input = new Employee(null, "Le Van C", "Finance", 13000000);
        Employee output = new Employee(3L, "Le Van C", "Finance", 13000000);

        when(repository.save(any(Employee.class))).thenReturn(output);

        Employee savedEmployee = employeeService.addEmployee(input);

        assertNotNull(savedEmployee);
        assertEquals(3L, savedEmployee.getId());
        assertEquals("Le Van C", savedEmployee.getFullName());
        verify(repository, times(1)).save(any(Employee.class));
    }

    // Test case 5
    @Test
    void deleteEmployee_RemovesCorrectElement() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}