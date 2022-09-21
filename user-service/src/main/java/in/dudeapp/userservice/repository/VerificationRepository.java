package in.dudeapp.userservice.repository;

import in.dudeapp.userservice.entity.Verification;
import in.dudeapp.userservice.constant.VerificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by mohan on 11/06/22
 */
@Repository
public interface VerificationRepository extends JpaRepository<Verification,Integer> {
    Optional<Verification> findByVerificationType(VerificationType verificationType);
}
