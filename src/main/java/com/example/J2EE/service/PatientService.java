package com.example.J2EE.service;

import com.example.J2EE.entity.Patient;
import com.example.J2EE.entity.Role;
import com.example.J2EE.repository.PatientRepository;
import com.example.J2EE.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Patient register(String username, String password, String email) {
        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setPassword(passwordEncoder.encode(password));
        patient.setEmail(email);

        Role patientRole = roleRepository.findByName("PATIENT")
                .orElseThrow(() -> new RuntimeException("Role PATIENT not found"));
        patient.getRoles().add(patientRole);

        return patientRepository.save(patient);
    }

    public boolean existsByUsername(String username) {
        return patientRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return patientRepository.findByEmail(email).isPresent();
    }

    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username).orElse(null);
    }
}
