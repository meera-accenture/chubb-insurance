package com.chubb.claims.controller;

import com.chubb.claims.dto.ClaimRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClaimControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateClaim() throws Exception {
        ClaimRequest request = new ClaimRequest();
        request.setClaimantName("Jane Doe");
        request.setClaimantEmail("jane@example.com");
        request.setMarket("Singapore");
        request.setLossType("MOTOR");
        request.setDescription("Rear bumper damage after collision");
        request.setEstimatedLoss(java.math.BigDecimal.valueOf(1500));

        mockMvc.perform(post("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.claimantName").value("Jane Doe"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void shouldAssignClaimAndReturnWorkloadSummary() throws Exception {
        ClaimRequest request = new ClaimRequest();
        request.setClaimantName("John Smith");
        request.setClaimantEmail("john@example.com");
        request.setMarket("Malaysia");
        request.setLossType("PROPERTY");
        request.setDescription("Water leak in apartment");
        request.setEstimatedLoss(java.math.BigDecimal.valueOf(3200));

        mockMvc.perform(post("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Map<String, Object> assignment = new HashMap<>();
        assignment.put("assignedTo", "Alice");
        assignment.put("actor", "claims-team-lead");
        assignment.put("message", "Picked up for assessment");

        mockMvc.perform(patch("/api/claims/1/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignedTo").value("Alice"));

        Map<String, Object> decision = new HashMap<>();
        decision.put("status", "SETTLED");
        decision.put("actor", "Alice");
        decision.put("message", "Claim approved for settlement");

        mockMvc.perform(patch("/api/claims/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(decision)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SETTLED"));

        mockMvc.perform(get("/api/claims/staff/workload"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].assignedTo").value("Alice"));
    }
}
