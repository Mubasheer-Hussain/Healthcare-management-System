package com.mediconnect.backend.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/doctors - should return all seeded doctors")
    void testGetAllDoctors() throws Exception {
        mockMvc.perform(get("/api/v1/doctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(6))))
                .andExpect(jsonPath("$.data[0].name").isNotEmpty())
                .andExpect(jsonPath("$.data[0].specialty").isNotEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/doctors/specialties - should return distinct specialties list")
    void testGetSpecialties() throws Exception {
        mockMvc.perform(get("/api/v1/doctors/specialties")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasItem("All")))
                .andExpect(jsonPath("$.data", hasItem("Cardiology")))
                .andExpect(jsonPath("$.data", hasItem("Pediatrics")));
    }

    @Test
    @DisplayName("GET /api/v1/doctors?specialty=Cardiology - should filter by specialty")
    void testFilterBySpecialty() throws Exception {
        mockMvc.perform(get("/api/v1/doctors")
                .param("specialty", "Cardiology")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].specialty").value("Cardiology"))
                .andExpect(jsonPath("$.data[1].specialty").value("Cardiology"));
    }

    @Test
    @DisplayName("GET /api/v1/doctors?query=Ruiz - should search by name query")
    void testSearchByQuery() throws Exception {
        mockMvc.perform(get("/api/v1/doctors")
                .param("query", "Ruiz")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Dr. Elena Ruiz"))
                .andExpect(jsonPath("$.data[0].specialty").value("Dermatology"));
    }

    @Test
    @DisplayName("PUT /api/v1/doctors/me - doctor can update their own profile")
    void testUpdateDoctorProfile() throws Exception {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        com.mediconnect.backend.auth.dto.LoginRequest login = new com.mediconnect.backend.auth.dto.LoginRequest(
                "sarah.johnson@mediconnect.com", "password123");

        org.springframework.test.web.servlet.MvcResult result = mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String token = mapper.readTree(result.getResponse().getContentAsString())
                .path("data").path("token").asText();

        com.mediconnect.backend.profile.dto.DoctorProfileUpdateRequest updateReq = 
                com.mediconnect.backend.profile.dto.DoctorProfileUpdateRequest.builder()
                        .fullName("Dr. Sarah Johnson, MD")
                        .clinic("NYC Premier Cardiology")
                        .accent("teal")
                        .experienceYears(16)
                        .build();

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/doctors/me")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Dr. Sarah Johnson, MD"))
                .andExpect(jsonPath("$.data.clinic").value("NYC Premier Cardiology"))
                .andExpect(jsonPath("$.data.experienceYears").value(16));
    }
}
