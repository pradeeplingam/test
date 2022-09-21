package in.dudeapp.collegeservice.repo;


import in.dudeapp.collegeservice.entity.College;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mohan on 11/06/22
 */
public interface CollegeRepo extends JpaRepository<College,Integer> {

    Page<College> findAllByNameStartingWithIgnoreCase(String name, Pageable pageable);
}
