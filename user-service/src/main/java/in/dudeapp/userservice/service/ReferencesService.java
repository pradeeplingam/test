package in.dudeapp.userservice.service;

import in.dudeapp.userservice.dto.request.ReferencesRequest;
import in.dudeapp.userservice.dto.response.ReferencesResponse;

import java.util.List;

/**
 * @author nandhan, Created on 23/07/22
 */
public interface ReferencesService {

    void addReferences(List<ReferencesRequest> allReferences);

    void deleteReferences(String userId);

    ReferencesResponse getReferencesByReferenceId(String id);

    List<ReferencesResponse> getReferencesByUserId(String userId);

    List<ReferencesResponse> getReferencesByCollegeId(String collegeId);

    List<ReferencesResponse> getReferencesByUserIdAndCollegeId(String userId, String collegeId);

    void deleteReferencesWithList(List<String> allIds);
}
