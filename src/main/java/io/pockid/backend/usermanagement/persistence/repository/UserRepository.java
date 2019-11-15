package io.pockid.backend.usermanagement.persistence.repository;

import io.pockid.backend.usermanagement.persistence.model.PUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<PUserModel, Long> {

    Optional<PUserModel> findByUid(String uid);
}
