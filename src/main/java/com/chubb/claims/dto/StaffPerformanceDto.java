package com.chubb.claims.dto;

public class StaffPerformanceDto {
    private String assignedTo;
    private int totalClaims;
    private int settledClaims;
    private int rejectedClaims;
    private int inProgressClaims;

    public StaffPerformanceDto() {
    }

    public StaffPerformanceDto(String assignedTo, int totalClaims, int settledClaims, int rejectedClaims, int inProgressClaims) {
        this.assignedTo = assignedTo;
        this.totalClaims = totalClaims;
        this.settledClaims = settledClaims;
        this.rejectedClaims = rejectedClaims;
        this.inProgressClaims = inProgressClaims;
    }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public int getTotalClaims() { return totalClaims; }
    public void setTotalClaims(int totalClaims) { this.totalClaims = totalClaims; }
    public int getSettledClaims() { return settledClaims; }
    public void setSettledClaims(int settledClaims) { this.settledClaims = settledClaims; }
    public int getRejectedClaims() { return rejectedClaims; }
    public void setRejectedClaims(int rejectedClaims) { this.rejectedClaims = rejectedClaims; }
    public int getInProgressClaims() { return inProgressClaims; }
    public void setInProgressClaims(int inProgressClaims) { this.inProgressClaims = inProgressClaims; }
}
