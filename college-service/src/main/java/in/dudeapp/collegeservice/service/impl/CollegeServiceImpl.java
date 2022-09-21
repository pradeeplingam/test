package in.dudeapp.collegeservice.service.impl;

import in.dudeapp.collegeservice.dto.request.CollegeRequest;
import in.dudeapp.collegeservice.dto.response.CollegeDTO;
import in.dudeapp.collegeservice.dto.response.CollegeResponse;
import in.dudeapp.collegeservice.dto.response.PageDTO;
import in.dudeapp.collegeservice.entity.College;
import in.dudeapp.collegeservice.exception.CollegeException;
import in.dudeapp.collegeservice.exception.ErrorMessage;
import in.dudeapp.collegeservice.repo.CollegeRepo;
import in.dudeapp.collegeservice.service.CollegeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by mohan on 11/06/22
 */
@Service
@AllArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private CollegeRepo collegeRepo;

    @Override
    public CollegeResponse getCollegeByName(String name, int page, int size) {
        Page<College> colleges = collegeRepo.findAllByNameStartingWithIgnoreCase(
                name, PageRequest.of(page, size));

        Page<CollegeDTO> collegePage = colleges.map(this::convertEntityToDto);
        PageDTO pageDTO = PageDTO.builder()
                .totalResults(collegePage.getTotalElements())
                .size(collegePage.getNumberOfElements())
                .pageNumber(collegePage.getNumber())
                .build();

        return CollegeResponse.builder()
                .data(collegePage.getContent())
                .page(pageDTO).build();
    }

    @Override
    @Transactional
    public void addCollege(CollegeRequest college) {
        collegeRepo.save(convertDtoToEntity(college));

    }

    @Override
    @Transactional
    public void deleteCollegeById(int id) {
        if (!collegeRepo.existsById(id))
            throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0001);
        collegeRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void updateCollege(CollegeRequest college, int id) {
        Optional<College> collegeOptional = collegeRepo.findById(id);
        if (collegeOptional.isEmpty())
            throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0001);

        College collegeEntity = collegeOptional.get();
        collegeEntity.setName(college.getName());
        collegeEntity.setStreet(college.getStreet());
        collegeEntity.setCity(college.getCity());
        collegeEntity.setDistrict(college.getDistrict());
        collegeEntity.setState(college.getState());
        collegeEntity.setPinCode(college.getPincode());
        collegeRepo.save(collegeEntity);
        // log?
    }

    @Override
    public CollegeDTO getCollegeById(int id) {
        Optional<College> college = collegeRepo.findById(id);
        if (college.isEmpty())
            throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0001);
        return convertEntityToDto(college.get());
    }

    @Override
    @Transactional
    public void addCollegeList(List<CollegeRequest> colleges) {
        collegeRepo.saveAll(colleges.stream()
                .map(this::convertDtoToEntity).toList());
    }

    private College convertDtoToEntity(CollegeRequest collegeRequest) {
        return College.builder()
                .name(collegeRequest.getName())
                .state(collegeRequest.getState())
                .street(collegeRequest.getStreet())
                .city(collegeRequest.getCity())
                .district(collegeRequest.getDistrict())
                .pinCode(collegeRequest.getPincode())
                .build();
    }

    private CollegeDTO convertEntityToDto(College college) {
        return CollegeDTO.builder().id(college.getId())
                .name(college.getName())
                .street(college.getStreet())
                .city(college.getCity())
                .district(college.getDistrict())
                .state(college.getState())
                .pincode(college.getPinCode()).build();
    }

}
