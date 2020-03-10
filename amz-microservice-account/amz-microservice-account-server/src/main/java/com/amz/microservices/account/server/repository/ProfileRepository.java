package com.amz.microservices.account.server.repository;

import com.amz.microservices.account.server.entity.Profile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

  boolean existsByEmail(String email);

  boolean existsByMobile(String mobile);

}
