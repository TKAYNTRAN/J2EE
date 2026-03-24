package com.example.J2EE.config;

import com.example.J2EE.entity.Patient;
import com.example.J2EE.entity.Role;
import com.example.J2EE.repository.PatientRepository;
import com.example.J2EE.repository.RoleRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email != null && patientRepository.findByEmail(email).isEmpty()) {
            Patient patient = new Patient();
            patient.setUsername(email);
            patient.setPassword("");
            patient.setEmail(email);

            Role patientRole = roleRepository.findByName("PATIENT")
                    .orElseThrow(() -> new RuntimeException("Role PATIENT not found"));
            patient.getRoles().add(patientRole);
            patientRepository.save(patient);
        }

        response.sendRedirect("/home");
    }
}
