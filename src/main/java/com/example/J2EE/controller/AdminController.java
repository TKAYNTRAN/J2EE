package com.example.J2EE.controller;

import com.example.J2EE.entity.Department;
import com.example.J2EE.entity.Doctor;
import com.example.J2EE.repository.DepartmentRepository;
import com.example.J2EE.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin/doctors";
    }

    @GetMapping("/doctors/create")
    public String createDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/doctor-form";
    }

    @PostMapping("/doctors/create")
    public String createDoctor(@RequestParam String name,
                               @RequestParam String specialty,
                               @RequestParam String image,
                               @RequestParam String departmentId,
                               RedirectAttributes redirectAttributes) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department == null) {
            redirectAttributes.addFlashAttribute("error", "Khoa không tồn tại!");
            return "redirect:/admin/doctors/create";
        }

        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialty(specialty);
        doctor.setImage(image);
        doctor.setDepartment(department);
        doctorService.saveDoctor(doctor);

        redirectAttributes.addFlashAttribute("success", "Thêm bác sĩ thành công!");
        return "redirect:/admin/doctors";
    }

    @GetMapping("/doctors/edit/{id}")
    public String editDoctorForm(@PathVariable String id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return "redirect:/admin/doctors";
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/doctor-form";
    }

    @PostMapping("/doctors/edit/{id}")
    public String updateDoctor(@PathVariable String id,
                               @RequestParam String name,
                               @RequestParam String specialty,
                               @RequestParam String image,
                               @RequestParam String departmentId,
                               RedirectAttributes redirectAttributes) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return "redirect:/admin/doctors";
        }

        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department == null) {
            redirectAttributes.addFlashAttribute("error", "Khoa không tồn tại!");
            return "redirect:/admin/doctors/edit/" + id;
        }

        doctor.setName(name);
        doctor.setSpecialty(specialty);
        doctor.setImage(image);
        doctor.setDepartment(department);
        doctorService.saveDoctor(doctor);

        redirectAttributes.addFlashAttribute("success", "Cập nhật bác sĩ thành công!");
        return "redirect:/admin/doctors";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable String id, RedirectAttributes redirectAttributes) {
        doctorService.deleteDoctor(id);
        redirectAttributes.addFlashAttribute("success", "Xóa bác sĩ thành công!");
        return "redirect:/admin/doctors";
    }
}
