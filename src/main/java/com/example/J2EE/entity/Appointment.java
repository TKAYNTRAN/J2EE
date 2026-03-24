package com.example.J2EE.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    private String id;

    @DBRef
    private Patient patient;

    @DBRef
    private Doctor doctor;

    private LocalDate appointmentDate;
}
