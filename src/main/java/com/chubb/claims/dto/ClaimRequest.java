package com.chubb.claims.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ClaimRequest {
    @NotBlank
    private String claimantName;

    @NotBlank
    @Email
    private String claimantEmail;

    @NotBlank
    private String market;

    @NotBlank
    private String lossType;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal estimatedLoss;

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
    public BigDecimal getEstimatedLoss() { return estimatedLoss; }
    public void setEstimatedLoss(BigDecimal estimatedLoss) { this.estimatedLoss = estimatedLoss; }
}
