package com.example.J2EE.controller;

import com.example.J2EE.entity.Appointment;
import com.example.J2EE.entity.Doctor;
import com.example.J2EE.entity.Patient;
import com.example.J2EE.service.AppointmentService;
import com.example.J2EE.service.DoctorService;
import com.example.J2EE.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping("/enroll/book/{doctorId}")
    public String bookForm(@PathVariable String doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            return "redirect:/home";
        }
        model.addAttribute("doctor", doctor);
        return "book-appointment";
    }

    @PostMapping("/enroll/book")
    public String book(@RequestParam String doctorId,
                       @RequestParam String appointmentDate,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes) {
        String username = getUsername(authentication);
        Patient patient = patientService.findByUsername(username);
        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (patient == null || doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra!");
            return "redirect:/home";
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.parse(appointmentDate));
        appointmentService.save(appointment);

        redirectAttributes.addFlashAttribute("success", "Đặt lịch khám thành công!");
        return "redirect:/appointments";
    }

    @GetMapping("/appointments")
    public String myAppointments(Authentication authentication, Model model) {
        String username = getUsername(authentication);
        Patient patient = patientService.findByUsername(username);
        if (patient != null) {
            List<Appointment> appointments = appointmentService.getByPatientId(patient.getId());
            model.addAttribute("appointments", appointments);
        }
        return "my-appointments";
    }

    private String getUsername(Authentication authentication) {
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            return oAuth2User.getAttribute("email");
        }
        return authentication.getName();
    }
}
