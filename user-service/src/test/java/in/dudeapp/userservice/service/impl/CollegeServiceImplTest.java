package in.dudeapp.userservice.service.impl;

import in.dudeapp.userservice.constant.ErrorMessage;
import in.dudeapp.userservice.dto.request.CollegeRequest;
import in.dudeapp.userservice.dto.response.CollegeDTO;
import in.dudeapp.userservice.dto.response.CollegeResponse;
import in.dudeapp.userservice.dto.response.PageDTO;
import in.dudeapp.userservice.entity.College;
import in.dudeapp.userservice.exception.CollegeException;
import in.dudeapp.userservice.repository.CollegeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

/**
 * @author nandhan, Created on 18/07/22
 */
@ExtendWith(MockitoExtension.class)
class CollegeServiceImplTest {

    @Mock
    private CollegeRepo repo;

    @InjectMocks
    private CollegeServiceImpl collegeService;

    private CollegeResponse response;
    private CollegeDTO dto;
    private PageDTO pageDTO;
    private Page<CollegeDTO> page;
    private CollegeRequest collegeRequest;

    private College collegeentity;


    @BeforeEach
    public void setup() {
        dto = CollegeDTO.builder().name("Caltech")
                .street("pas").city("kljljkl").district("klkjlkj")
                .state("jjewiroendj").pincode(265656).build();
        pageDTO = PageDTO.builder().size(1).pageNumber(0).totalResults(1).build();
        response = CollegeResponse.builder().data(List.of(dto)).page(pageDTO).build();
        page = new PageImpl<>(List.of(dto), PageRequest.of(0, 1), 1);
        collegeRequest = CollegeRequest.builder()
                .name("MIT").street("mits").city("kljlje").district("jkljlj")
                .state("lkjjjji").pincode(89899).build();
        collegeentity = College.builder().id("234")
                .name("Caltech").street("some street").city("some city")
                .district("some district").state("some state")
                .pinCode(12346).build();
    }

    @Test
    void getCollegeByName() {
        when(repo.findAllByNameStartingWithIgnoreCase(anyString(),any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(collegeentity)));
        CollegeResponse collegeResponse = collegeService.getCollegeByName("cal",0,1);
        assertThat(repo).isNotNull();
        assertThat(collegeResponse).isNotNull();
        assertThat(collegeResponse.getData().get(0).getName()).isEqualTo("Caltech");
    }

    @Test
    void getCollegeById() {

        given(repo.findById(anyString())).willReturn(Optional.ofNullable(collegeentity));
        CollegeDTO newDto = collegeService.getCollegeById("123");
        assertThat(newDto).isNotNull();
        assertThat(newDto.getName()).isEqualTo("Caltech");
    }

    @Test
    public void testAdd() {
        when(repo.save(any(College.class))).thenReturn(collegeentity);
        collegeService.addCollege(collegeRequest);
        verify(repo,times(1)).save(any(College.class));
    }

    @Test
    public void testDelete() {
        String id = "5";
        when(repo.existsById(anyString())).thenReturn(true);
        willDoNothing().given(repo).deleteById(anyString());
        collegeService.deleteCollegeById(id);
        verify(repo,times(1)).deleteById(id);
    }

    @Test
    public void testDeleteThrowException() {
        when(repo.existsById(anyString()))
                .thenThrow(new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0001));
        assertThrows(CollegeException.class, () -> collegeService.deleteCollegeById("8"));
    }
}