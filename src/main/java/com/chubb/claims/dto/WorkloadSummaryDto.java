package com.chubb.claims.dto;

public class WorkloadSummaryDto {
    private String assignedTo;
    private int claimCount;

    public WorkloadSummaryDto() {
    }

    public WorkloadSummaryDto(String assignedTo, int claimCount) {
        this.assignedTo = assignedTo;
        this.claimCount = claimCount;
    }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public int getClaimCount() { return claimCount; }
    public void setClaimCount(int claimCount) { this.claimCount = claimCount; }
}
