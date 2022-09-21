package in.dudeapp.userservice.repository;

import in.dudeapp.userservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by mohan on 04/07/22
 */
@Repository
public interface DocumentRepo extends JpaRepository<Document,Integer> {

    Optional<Document> findByUserId(String userId);
}
