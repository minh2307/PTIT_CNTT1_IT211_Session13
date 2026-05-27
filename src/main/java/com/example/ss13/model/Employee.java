package com.example.ss13.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    private Long id;

    @NotBlank(message = "fullName cannot be null or empty")
    private String fullName;

    @NotBlank(message = "department cannot be null or empty")
    private String department;

    @NotNull(message = "salary cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
    private Double salary;
}
