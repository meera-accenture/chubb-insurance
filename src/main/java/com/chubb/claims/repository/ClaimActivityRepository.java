package com.chubb.claims.repository;

import com.chubb.claims.entity.ClaimActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimActivityRepository extends JpaRepository<ClaimActivity, Long> {
}
