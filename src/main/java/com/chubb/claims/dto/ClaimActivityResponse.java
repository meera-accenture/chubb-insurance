package com.chubb.claims.dto;

import com.chubb.claims.entity.ClaimActivity;

import java.time.LocalDateTime;

public class ClaimActivityResponse {
    private Long id;
    private String actor;
    private String message;
    private LocalDateTime createdAt;

    public static ClaimActivityResponse fromEntity(ClaimActivity activity) {
        ClaimActivityResponse response = new ClaimActivityResponse();
        response.id = activity.getId();
        response.actor = activity.getActor();
        response.message = activity.getMessage();
        response.createdAt = activity.getCreatedAt();
        return response;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
