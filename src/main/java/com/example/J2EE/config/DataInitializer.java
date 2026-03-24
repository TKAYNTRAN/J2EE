package com.example.J2EE.config;

import com.example.J2EE.entity.*;
import com.example.J2EE.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (departmentRepository.count() > 0) {
            return;
        }

        // Create Roles
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole = roleRepository.save(adminRole);

        Role patientRole = new Role();
        patientRole.setName("PATIENT");
        patientRole = roleRepository.save(patientRole);

        // Create Departments
        Department dept1 = createDepartment("Khoa Nội");
        Department dept2 = createDepartment("Khoa Ngoại");
        Department dept3 = createDepartment("Khoa Nhi");
        Department dept4 = createDepartment("Khoa Da Liễu");
        Department dept5 = createDepartment("Khoa Mắt");

        // Create Doctors (12 doctors for pagination demo)
        createDoctor("BS. Nguyễn Văn An", "Tim mạch", dept1, "https://placehold.co/250x250?text=BS+An");
        createDoctor("BS. Trần Thị Bình", "Hô hấp", dept1, "https://placehold.co/250x250?text=BS+Binh");
        createDoctor("BS. Lê Văn Cường", "Chấn thương chỉnh hình", dept2, "https://placehold.co/250x250?text=BS+Cuong");
        createDoctor("BS. Phạm Thị Dung", "Phẫu thuật tổng quát", dept2, "https://placehold.co/250x250?text=BS+Dung");
        createDoctor("BS. Hoàng Văn Em", "Nhi khoa tổng quát", dept3, "https://placehold.co/250x250?text=BS+Em");
        createDoctor("BS. Ngô Thị Phương", "Nhi sơ sinh", dept3, "https://placehold.co/250x250?text=BS+Phuong");
        createDoctor("BS. Đặng Văn Giang", "Da liễu thẩm mỹ", dept4, "https://placehold.co/250x250?text=BS+Giang");
        createDoctor("BS. Vũ Thị Hoa", "Dị ứng da", dept4, "https://placehold.co/250x250?text=BS+Hoa");
        createDoctor("BS. Bùi Văn Khoa", "Nhãn khoa", dept5, "https://placehold.co/250x250?text=BS+Khoa");
        createDoctor("BS. Mai Thị Lan", "Phẫu thuật mắt", dept5, "https://placehold.co/250x250?text=BS+Lan");
        createDoctor("BS. Trịnh Văn Minh", "Thần kinh", dept1, "https://placehold.co/250x250?text=BS+Minh");
        createDoctor("BS. Lý Thị Ngọc", "Tiêu hóa", dept1, "https://placehold.co/250x250?text=BS+Ngoc");

        // Create admin user
        Patient admin = new Patient();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@clinic.com");
        admin.getRoles().add(adminRole);
        patientRepository.save(admin);

        // Create sample patient
        Patient patient = new Patient();
        patient.setUsername("patient1");
        patient.setPassword(passwordEncoder.encode("123456"));
        patient.setEmail("patient1@gmail.com");
        patient.getRoles().add(patientRole);
        patientRepository.save(patient);
    }

    private Department createDepartment(String name) {
        Department dept = new Department();
        dept.setName(name);
        return departmentRepository.save(dept);
    }

    private void createDoctor(String name, String specialty, Department department, String image) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialty(specialty);
        doctor.setDepartment(department);
        doctor.setImage(image);
        doctorRepository.save(doctor);
    }
}
