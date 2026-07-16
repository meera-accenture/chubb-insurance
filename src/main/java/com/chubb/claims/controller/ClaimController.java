package com.chubb.claims.controller;

import com.chubb.claims.dto.ClaimActivityRequest;
import com.chubb.claims.dto.ClaimActivityResponse;
import com.chubb.claims.dto.ClaimRequest;
import com.chubb.claims.dto.ClaimResponse;
import com.chubb.claims.dto.StaffPerformanceDto;
import com.chubb.claims.dto.UpdateClaimStatusRequest;
import com.chubb.claims.dto.WorkloadSummaryDto;
import com.chubb.claims.entity.Claim;
import com.chubb.claims.service.ClaimService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    public ResponseEntity<ClaimResponse> createClaim(@Valid @RequestBody ClaimRequest request) {
        Claim claim = claimService.createClaim(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClaimResponse.fromEntity(claim));
    }

    @GetMapping
    public ResponseEntity<List<ClaimResponse>> getAllClaims() {
        List<ClaimResponse> claims = claimService.getAllClaims().stream()
                .map(ClaimResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(claims);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponse> getClaimById(@PathVariable Long id) {
        return claimService.getClaimById(id)
                .map(claim -> ResponseEntity.ok(ClaimResponse.fromEntity(claim)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByStatus(@PathVariable String status) {
        List<ClaimResponse> claims = claimService.getClaimsByStatus(status).stream()
                .map(ClaimResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(claims);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ClaimResponse> updateClaimStatus(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateClaimStatusRequest request) {
        try {
            Claim updated = claimService.updateClaimStatus(id, request);
            return ResponseEntity.ok(ClaimResponse.fromEntity(updated));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ClaimActivityResponse> addActivity(@PathVariable Long id,
                                                             @Valid @RequestBody ClaimActivityRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ClaimActivityResponse.fromEntity(claimService.addActivity(id, request)));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<ClaimResponse> assignClaim(@PathVariable Long id,
                                                     @RequestBody Map<String, String> payload) {
        try {
            String assignedTo = payload.get("assignedTo");
            String actor = payload.getOrDefault("actor", "claims-staff");
            String message = payload.getOrDefault("message", "Claim assigned for review");
            Claim claim = claimService.assignClaim(id, assignedTo, actor, message);
            return ResponseEntity.ok(ClaimResponse.fromEntity(claim));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/staff/workload")
    public ResponseEntity<List<WorkloadSummaryDto>> getWorkloadSummary() {
        return ResponseEntity.ok(claimService.getWorkloadSummary());
    }

    @GetMapping("/staff/performance")
    public ResponseEntity<List<StaffPerformanceDto>> getPerformanceSummary() {
        return ResponseEntity.ok(claimService.getPerformanceSummary());
    }
}
