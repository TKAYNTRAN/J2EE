package com.example.J2EE.service;

import com.example.J2EE.entity.Appointment;
import com.example.J2EE.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getByPatientId(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
}
