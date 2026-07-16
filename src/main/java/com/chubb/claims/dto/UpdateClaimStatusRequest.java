package com.chubb.claims.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateClaimStatusRequest {
    @NotBlank
    private String status;

    @NotBlank
    private String actor;

    @NotBlank
    private String message;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
