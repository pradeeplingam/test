package in.dudeapp.userservice.service.impl;

import in.dudeapp.userservice.constant.ErrorMessage;
import in.dudeapp.userservice.dto.request.ReferencesRequest;
import in.dudeapp.userservice.dto.response.ReferencesResponse;
import in.dudeapp.userservice.entity.References;
import in.dudeapp.userservice.exception.UserException;
import in.dudeapp.userservice.repository.ReferencesRepository;
import in.dudeapp.userservice.service.ReferencesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @author nandhan, Created on 24/07/22
 */
@Service
@AllArgsConstructor
public class ReferencesServiceImpl implements ReferencesService {

    private ReferencesRepository repository;
    private ExecutorService executorService;

    @Override
    @Transactional
    public void addReferences(List<ReferencesRequest> allReferences) {
        // create child thread and assign task to it.
        executorService.submit(() -> insertData(allReferences));
    }

    @Override
    @Transactional
    public void deleteReferences(String userId) {
        repository.deleteAllByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteReferencesWithList(List<String> allIds) {
        executorService.submit(() -> repository.deleteByIdIn(allIds));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferencesResponse> getReferencesByUserId(String userId) {
        return repository.findAllByUserId(userId)
                .stream().map(this::convertEntityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferencesResponse> getReferencesByCollegeId(String collegeId) {
        return repository.findAllByCollegeId(collegeId)
                .stream().map(this::convertEntityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferencesResponse> getReferencesByUserIdAndCollegeId(String userId, String collegeId) {
        return repository.findAllByUserIdAndAndCollegeId(userId, collegeId)
                .stream().map(this::convertEntityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReferencesResponse getReferencesByReferenceId(String id) {
        Optional<References> reference =  repository.findById(id);
        if (reference.isPresent())
            return convertEntityToDto(reference.get());
        else throw new UserException(ErrorMessage.REFERENCES_ERROR_CODE_0001);
    }

    private References convertDtoToEntity(ReferencesRequest request) {
        return References.builder()
                .name(request.getName())
                .contact(request.getContact())
                .collegeId(request.getCollegeId())
                .userId(request.getUserId())
                .build();
    }

    private ReferencesResponse convertEntityToDto(References references) {
        return ReferencesResponse.builder()
                .id(references.getId())
                .name(references.getName())
                .contact(references.getContact())
                .userId(references.getUserId())
                .collegeId(references.getCollegeId()).build();
    }

    private void insertData(List<ReferencesRequest> allReferences) {
        // inserts will be done in batches. Batch-size is set in the properties file.
        // if size is less, no need to parallelize the inserts.
        if (allReferences.size() < 1000) {
            repository.saveAll(allReferences.stream()
                    .map(this::convertDtoToEntity).toList());
        } else {
            // convert to parallel stream and execute inserts.
            repository.saveAll(
                    allReferences.parallelStream()
                            .map(this::convertDtoToEntity).toList());
        }
    }

}
