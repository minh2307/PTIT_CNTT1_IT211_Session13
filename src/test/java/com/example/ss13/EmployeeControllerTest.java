package com.example.ss13;

import com.example.ss13.controller.EmployeeController;
import com.example.ss13.model.Entity.Employee;
import com.example.ss13.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_Return200() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "Nguyen Van A", "Engineering", 15000000)
        );

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Nguyen Van A"));
    }

    @Test
    void getEmployeeById_Return200() throws Exception {
        Employee employee = new Employee(1L, "Nguyen Van A", "Engineering", 15000000);

        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"));
    }

    @Test
    void getEmployeeById_Return404() throws Exception {
        when(employeeService.getEmployeeById(99L)).thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found"));
    }

    @Test
    void addEmployee_Return201() throws Exception {
        Employee responseEmployee = new Employee(3L, "Le Van C", "Finance", 13000000);

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(responseEmployee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Le Van C",
                                  "department": "Finance",
                                  "salary": 13000000
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.fullName").value("Le Van C"));
    }

    @Test
    void addEmployee_InvalidPayload_Return400() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "",
                                  "department": "Finance",
                                  "salary": 13000000
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("fullName: Full name must not be blank"));
    }

    @Test
    void updateEmployee_Return200() throws Exception {
        Employee responseEmployee = new Employee(1L, "Nguyen Van A Updated", "Engineering", 18000000);

        when(employeeService.updateEmployee(any(Long.class), any(Employee.class))).thenReturn(responseEmployee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Nguyen Van A Updated",
                                  "department": "Engineering",
                                  "salary": 18000000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A Updated"));
    }

    @Test
    void deleteEmployee_Return204() throws Exception {
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEmployee_NotFound_Return404() throws Exception {
        doThrow(new RuntimeException("Employee not found")).when(employeeService).deleteEmployee(99L);

        mockMvc.perform(delete("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found"));
    }
}
