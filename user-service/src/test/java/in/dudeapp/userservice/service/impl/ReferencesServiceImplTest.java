package in.dudeapp.userservice.service.impl;

import in.dudeapp.userservice.dto.request.ReferencesRequest;
import in.dudeapp.userservice.dto.response.ReferencesResponse;
import in.dudeapp.userservice.entity.References;
import in.dudeapp.userservice.exception.UserException;
import in.dudeapp.userservice.repository.ReferencesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author nandhan, Created on 24/07/22
 */
@ExtendWith(MockitoExtension.class)
public class ReferencesServiceImplTest {

    @Mock
    private ReferencesRepository repository;
    @InjectMocks
    private ReferencesServiceImpl service;

    private List<References> allEntities;
    private List<ReferencesResponse> allResponses;
    private List<ReferencesRequest> allRequests;
    private References references;
    private ReferencesResponse response;
    private ReferencesRequest request;

    @BeforeEach
    public void setup() {
        references = new References("someId","person1","32323","fajdifads","userId");
        request = new ReferencesRequest("name","contact","userId","collegeId");
        response = new ReferencesResponse("someId","name","contact","userId","collegeId");

    }

    @Test
    public void testAddReferences() {
        //TODO add test
        //BDDMockito.willDoNothing().given(repository).saveAll(any(List.class));
        //Mockito.when(repository.saveAll(any(List.class))).thenReturn(List.of(references));
        //service.addReferences(List.of(request));
        //verify(repository,times(1)).saveAll(List.of(references));

    }

    @Test
    public void testDeleteRefernce() {
        BDDMockito.willDoNothing().given(repository).deleteAllByUserId(anyString());
        service.deleteReferences("string");
        verify(repository,times(1)).deleteAllByUserId("string");
    }

    @Test
    public void testGetReferencesByUserId() {
        Mockito.when(repository.findAllByUserId(anyString())).thenReturn(List.of(references));
        allResponses = service.getReferencesByUserId("userId");
        Assertions.assertThat(allResponses).isNotNull();
        Assertions.assertThat(allResponses.size()).isEqualTo(1);
    }

    @Test
    public void testGetReferencesByUserIdAndCollegeId() {
        Mockito.when(repository.findAllByUserIdAndAndCollegeId(anyString(), anyString())).thenReturn(List.of(references));
        allResponses = service.getReferencesByUserIdAndCollegeId("userId","collegeId");
        Assertions.assertThat(allResponses).isNotNull();
        Assertions.assertThat(allResponses.size()).isEqualTo(1);
    }

    @Test
    public void testGetReferencesByCollegeId() {
        Mockito.when(repository.findAllByCollegeId(anyString())).thenReturn(List.of(references));
        allResponses = service.getReferencesByCollegeId("userId");
        Assertions.assertThat(allResponses).isNotNull();
        Assertions.assertThat(allResponses.size()).isEqualTo(1);
    }

    @Test
    public void testGetReferencesByReferenceId() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(references));
        Assertions.assertThat(service.getReferencesByReferenceId("some")).isNotNull();
    }

    @Test
    public void testUserExceptionThrown() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserException.class, () -> service.getReferencesByReferenceId("kjk"));
    }
}
