package in.dudeapp.userservice.repository;

import in.dudeapp.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by mohan on 10/06/22
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByMobileNumber(String mobileNumber);

    Optional<User> findByEmail(String email);
}
