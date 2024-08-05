// MemberDetailsRepository.java
package com.example.meetpro.repository;

import com.example.meetpro.domain.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Long> {
    MemberDetails findByLoginId(String loginId);
}