package in.dudeapp.userservice.repository;

import in.dudeapp.userservice.entity.References;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author nandhan, Created on 23/07/22
 */
public interface ReferencesRepository extends JpaRepository<References, String> {
    void deleteAllByUserId(String userId);
    List<References> findAllByCollegeId(String collegeId);
    List<References> findAllByUserId(String UserId);
    List<References> findAllByUserIdAndAndCollegeId(String userId, String collegeId);
    void deleteByIdIn(List<String> allIds);
}
