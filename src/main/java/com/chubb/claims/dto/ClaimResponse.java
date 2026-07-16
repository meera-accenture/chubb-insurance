package com.chubb.claims.dto;

import com.chubb.claims.entity.Claim;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClaimResponse {
    private Long id;
    private String claimantName;
    private String claimantEmail;
    private String market;
    private String lossType;
    private String description;
    private String status;
    private String assignedTo;
    private BigDecimal estimatedLoss;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ClaimResponse fromEntity(Claim claim) {
        ClaimResponse response = new ClaimResponse();
        response.id = claim.getId();
        response.claimantName = claim.getClaimantName();
        response.claimantEmail = claim.getClaimantEmail();
        response.market = claim.getMarket();
        response.lossType = claim.getLossType();
        response.description = claim.getDescription();
        response.status = claim.getStatus().name();
        response.assignedTo = claim.getAssignedTo();
        response.estimatedLoss = claim.getEstimatedLoss();
        response.createdAt = claim.getCreatedAt();
        response.updatedAt = claim.getUpdatedAt();
        return response;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClaimantName() { return claimantName; }
    public void setClaimantName(String claimantName) { this.claimantName = claimantName; }
    public String getClaimantEmail() { return claimantEmail; }
    public void setClaimantEmail(String claimantEmail) { this.claimantEmail = claimantEmail; }
    public String getMarket() { return market; }
    public void setMarket(String market) { this.market = market; }
    public String getLossType() { return lossType; }
    public void setLossType(String lossType) { this.lossType = lossType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public BigDecimal getEstimatedLoss() { return estimatedLoss; }
    public void setEstimatedLoss(BigDecimal estimatedLoss) { this.estimatedLoss = estimatedLoss; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
