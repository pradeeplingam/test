package in.dudeapp.userservice.repo;

import in.dudeapp.userservice.entity.College;
import in.dudeapp.userservice.repository.CollegeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author nandhan, Created on 17/07/22
 */
@ExtendWith(MockitoExtension.class)
class CollegeRepoImplTest {

    private CollegeRepo repo;

    private College college;
    private List<College> collegeList;

    @BeforeEach
    public void setup() {
        college = College.builder()
                .name("New York Univeristy")
                .street("New York University road")
                .city("New York")
                .district("New York")
                .state("New York")
                .pinCode(3232).build();

    }

    @Test
    void addCollege() {
    }

    @Test
    void getCollegeById() {
    }

    @Test
    void getCollegeByName() {
    }
}