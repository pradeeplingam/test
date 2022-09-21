package in.dudeapp.userservice.repository;

import in.dudeapp.userservice.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mohan on 20/07/22
 */
@Repository
public interface StudentDetailsRepository extends JpaRepository<StudentDetails,Integer> {
}
