package com.example.J2EE.controller;

import com.example.J2EE.entity.Doctor;
import com.example.J2EE.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DoctorApiController {

    private final DoctorService doctorService;

    @GetMapping("/doctors/search")
    public List<Doctor> searchDoctors(@RequestParam String keyword) {
        return doctorService.searchByName(keyword);
    }
}
