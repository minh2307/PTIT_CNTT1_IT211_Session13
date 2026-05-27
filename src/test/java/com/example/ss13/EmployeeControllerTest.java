package com.example.ss13;

import com.example.EmployeeController.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import com.example.ss13.controller.EmployeeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test case 1
    @Test
    void getAllEmployees_Return200() throws Exception {

        List<Employee> employees = List.of(
                new Employee(1L, "Nguyen Van A", "Engineering", 15000000)
        );

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName")
                        .value("Nguyen Van A"));
    }

    // Test case 2
    @Test
    void getEmployeeById_Return200() throws Exception {

        Employee employee = new Employee(
                1L,
                "Nguyen Van A",
                "Engineering",
                15000000
        );

        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName")
                        .value("Nguyen Van A"));
    }

    // Test case 3
    @Test
    void getEmployeeById_Return404() throws Exception {

        when(employeeService.getEmployeeById(99L))
                .thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound());
    }

    // Test case 4
    @Test
    void addEmployee_Return201() throws Exception {

        Employee requestEmployee = new Employee(
                null,
                "Le Van C",
                "Finance",
                13000000
        );

        Employee responseEmployee = new Employee(
                3L,
                "Le Van C",
                "Finance",
                13000000
        );

        when(employeeService.addEmployee(requestEmployee))
                .thenReturn(responseEmployee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.fullName")
                        .value("Le Van C"));
    }
}