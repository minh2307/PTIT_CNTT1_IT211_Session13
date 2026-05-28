package com.example.ss13.model.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Employee {
    private Long id;

    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @NotBlank(message = "Department must not be blank")
    private String department;

    @Positive(message = "Salary must be greater than 0")
    private double salary;

}
