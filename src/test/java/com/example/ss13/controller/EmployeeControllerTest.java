package com.example.ss13.controller;

import com.example.ss13.exception.ResourceNotFoundException;
import com.example.ss13.model.Employee;
import com.example.ss13.service.EmployeeService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllEmployees_ReturnList() throws Exception {
        List<Employee> list = Arrays.asList(
                new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0),
                new Employee(2L, "Tran Thi B", "Human Resources", 12000000.0)
        );

        when(employeeService.getAllEmployees()).thenReturn(list);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].fullName").value("Nguyen Van A"))
                .andExpect(jsonPath("$[1].fullName").value("Tran Thi B"));
    }

    @Test
    public void getById_Found() throws Exception {
        Employee employee = new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0);

        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.department").value("Engineering"));
    }

    @Test
    public void getById_NotFound_ThrowException() throws Exception {
        when(employeeService.getEmployeeById(99L))
                .thenThrow(new ResourceNotFoundException("Employee not found with id: 99"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Employee not found with id: 99"));
    }

    @Test
    public void addEmployee_Success() throws Exception {
        Employee input = new Employee(null, "Nguyen Van A", "Engineering", 15000000.0);
        Employee created = new Employee(6L, "Nguyen Van A", "Engineering", 15000000.0);

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(created);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"));
    }
}
