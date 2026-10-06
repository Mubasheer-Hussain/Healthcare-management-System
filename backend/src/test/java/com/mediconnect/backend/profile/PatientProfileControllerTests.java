package com.mediconnect.backend.profile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediconnect.backend.auth.dto.LoginRequest;
import com.mediconnect.backend.profile.dto.PatientProfileUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String patientToken;

    @BeforeEach
    void setUp() throws Exception {
        LoginRequest loginRequest = new LoginRequest("alex.rivera@mediconnect.com", "password123");
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode responseNode = objectMapper.readTree(result.getResponse().getContentAsString());
        patientToken = responseNode.path("data").path("token").asText();
    }

    @Test
    @DisplayName("GET /api/v1/patients/me - should return patient profile for authenticated user")
    void testGetPatientProfile() throws Exception {
        mockMvc.perform(get("/api/v1/patients/me")
                .header("Authorization", "Bearer " + patientToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.fullName").value("Alex Rivera"))
                .andExpect(jsonPath("$.data.email").value("alex.rivera@mediconnect.com"))
                .andExpect(jsonPath("$.data.bloodGroup").value("O+"));
    }

    @Test
    @DisplayName("PUT /api/v1/patients/me - should update patient profile")
    void testUpdatePatientProfile() throws Exception {
        PatientProfileUpdateRequest updateRequest = PatientProfileUpdateRequest.builder()
                .fullName("Alex Rivera")
                .dateOfBirth(LocalDate.of(1994, 5, 15))
                .gender("Male")
                .bloodGroup("O+")
                .emergencyContactName("Maria Rivera")
                .emergencyContactPhone("+1-555-9999")
                .allergies("None")
                .build();

        mockMvc.perform(put("/api/v1/patients/me")
                .header("Authorization", "Bearer " + patientToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.emergencyContactPhone").value("+1-555-9999"))
                .andExpect(jsonPath("$.data.allergies").value("None"));
    }
}
