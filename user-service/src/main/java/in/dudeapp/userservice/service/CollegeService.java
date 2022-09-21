package in.dudeapp.userservice.service;


import in.dudeapp.userservice.dto.request.CollegeRequest;
import in.dudeapp.userservice.dto.response.CollegeDTO;
import in.dudeapp.userservice.dto.response.CollegeResponse;

import java.util.List;

/**
 * Created by mohan on 11/06/22
 */
public interface CollegeService {
    CollegeResponse getCollegeByName(String name, int page, int size);
    CollegeDTO addCollege(CollegeRequest college);
    void deleteCollegeById(String id);
    CollegeDTO updateCollege(CollegeRequest college, String id);
    CollegeDTO getCollegeById(String id);
    void addCollegeList(List<CollegeRequest> colleges);
}
