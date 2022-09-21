package in.dudeapp.collegeservice.service;


import in.dudeapp.collegeservice.dto.request.CollegeRequest;
import in.dudeapp.collegeservice.dto.response.CollegeDTO;
import in.dudeapp.collegeservice.dto.response.CollegeResponse;

import java.util.List;

/**
 * Created by mohan on 11/06/22
 */
public interface CollegeService {
    CollegeResponse getCollegeByName(String name, int page, int size);
    void addCollege(CollegeRequest college);
    void deleteCollegeById(int id);
    void updateCollege(CollegeRequest college, int id);
    CollegeDTO getCollegeById(int id);

    void addCollegeList(List<CollegeRequest> colleges); // incase of
}
