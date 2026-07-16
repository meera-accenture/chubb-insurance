package com.chubb.claims.service;

import com.chubb.claims.dto.ClaimActivityRequest;
import com.chubb.claims.dto.ClaimRequest;
import com.chubb.claims.dto.UpdateClaimStatusRequest;
import com.chubb.claims.dto.WorkloadSummaryDto;
import com.chubb.claims.dto.StaffPerformanceDto;
import com.chubb.claims.entity.Claim;
import com.chubb.claims.entity.ClaimActivity;
import com.chubb.claims.entity.ClaimStatus;
import com.chubb.claims.repository.ClaimActivityRepository;
import com.chubb.claims.repository.ClaimRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final ClaimActivityRepository claimActivityRepository;

    public ClaimService(ClaimRepository claimRepository, ClaimActivityRepository claimActivityRepository) {
        this.claimRepository = claimRepository;
        this.claimActivityRepository = claimActivityRepository;
    }

    @Transactional
    public Claim createClaim(ClaimRequest request) {
        Claim claim = new Claim();
        claim.setClaimantName(request.getClaimantName());
        claim.setClaimantEmail(request.getClaimantEmail());
        claim.setMarket(request.getMarket());
        claim.setLossType(request.getLossType());
        claim.setDescription(request.getDescription());
        claim.setEstimatedLoss(request.getEstimatedLoss());
        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public List<Claim> getClaimsByStatus(String status) {
        return claimRepository.findByStatus(parseStatus(status));
    }

    @Transactional
    public Claim updateClaimStatus(Long id, UpdateClaimStatusRequest request) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found"));

        claim.setStatus(parseStatus(request.getStatus()));
        claimRepository.save(claim);

        ClaimActivity activity = new ClaimActivity();
        activity.setClaim(claim);
        activity.setActor(request.getActor());
        activity.setMessage(request.getMessage());
        claimActivityRepository.save(activity);
        return claim;
    }

    @Transactional
    public ClaimActivity addActivity(Long claimId, ClaimActivityRequest request) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found"));

        ClaimActivity activity = new ClaimActivity();
        activity.setClaim(claim);
        activity.setActor(request.getActor());
        activity.setMessage(request.getMessage());
        return claimActivityRepository.save(activity);
    }

    @Transactional
    public Claim assignClaim(Long claimId, String assignedTo, String actor, String message) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found"));

        claim.setAssignedTo(assignedTo);
        claim.setStatus(ClaimStatus.UNDER_REVIEW);
        claimRepository.save(claim);

        ClaimActivity activity = new ClaimActivity();
        activity.setClaim(claim);
        activity.setActor(actor);
        activity.setMessage(message);
        claimActivityRepository.save(activity);
        return claim;
    }

    private ClaimStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return ClaimStatus.OPEN;
        }
        try {
            return ClaimStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unsupported claim status: " + status);
        }
    }

    public List<WorkloadSummaryDto> getWorkloadSummary() {
        List<Claim> claims = claimRepository.findAll();
        Map<String, Long> counts = claims.stream()
                .filter(claim -> claim.getAssignedTo() != null && !claim.getAssignedTo().isBlank())
                .collect(Collectors.groupingBy(Claim::getAssignedTo, Collectors.counting()));

        return counts.entrySet().stream()
                .map(entry -> new WorkloadSummaryDto(entry.getKey(), entry.getValue().intValue()))
                .sorted((a, b) -> Integer.compare(b.getClaimCount(), a.getClaimCount()))
                .collect(Collectors.toList());
    }

    public List<StaffPerformanceDto> getPerformanceSummary() {
        List<Claim> claims = claimRepository.findAll();
        Map<String, List<Claim>> claimsByStaff = claims.stream()
                .filter(claim -> claim.getAssignedTo() != null && !claim.getAssignedTo().isBlank())
                .collect(Collectors.groupingBy(Claim::getAssignedTo));

        return claimsByStaff.entrySet().stream()
                .map(entry -> {
                    List<Claim> staffClaims = entry.getValue();
                    int settled = (int) staffClaims.stream().filter(claim -> claim.getStatus() == ClaimStatus.SETTLED).count();
                    int rejected = (int) staffClaims.stream().filter(claim -> claim.getStatus() == ClaimStatus.REJECTED).count();
                    int inProgress = (int) staffClaims.stream().filter(claim -> claim.getStatus() != ClaimStatus.SETTLED && claim.getStatus() != ClaimStatus.REJECTED).count();
                    return new StaffPerformanceDto(entry.getKey(), staffClaims.size(), settled, rejected, inProgress);
                })
                .sorted((a, b) -> Integer.compare(b.getTotalClaims(), a.getTotalClaims()))
                .collect(Collectors.toList());
    }
}
