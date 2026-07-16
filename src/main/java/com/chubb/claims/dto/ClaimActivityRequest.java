package com.chubb.claims.dto;

import jakarta.validation.constraints.NotBlank;

public class ClaimActivityRequest {
    @NotBlank
    private String actor;

    @NotBlank
    private String message;

    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
