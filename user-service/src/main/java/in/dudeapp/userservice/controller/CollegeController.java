package in.dudeapp.userservice.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import in.dudeapp.common.annotation.TrackExecutionTime;
import in.dudeapp.userservice.constant.ErrorMessage;
import in.dudeapp.userservice.dto.request.CollegeRequest;
import in.dudeapp.userservice.dto.response.CollegeDTO;
import in.dudeapp.userservice.dto.response.CollegeResponse;
import in.dudeapp.userservice.exception.CollegeException;
import in.dudeapp.userservice.service.CollegeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohan on 11/06/22
 */

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/college")
public class CollegeController {

    public final CollegeService collegeService;

    // TODO: add suitable log messages.
    // TODO: comments.

    @TrackExecutionTime
    @GetMapping(value = "/getCollegesByName",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollegeResponse> getCollegesByName(
            @RequestParam(value = "name",defaultValue = "a", required = false)
                    String collegeName,
            @PageableDefault(sort = {"name"}) Pageable pageable){

        CollegeResponse collegeResponse = collegeService.getCollegeByName(collegeName,
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(collegeResponse, HttpStatus.OK);
    }

    @TrackExecutionTime
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollegeDTO> getCollegeById(@PathVariable String id) {
        if (StringUtils.isEmpty(id))
            throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0002);
        return new ResponseEntity<>(collegeService.getCollegeById(id), HttpStatus.OK);
    }

    @TrackExecutionTime
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollegeDTO> addCollege(
            @RequestBody @Valid CollegeRequest college){
        return new ResponseEntity<>(collegeService.addCollege(college), HttpStatus.CREATED);
    }

    @TrackExecutionTime
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollegeDTO> updateCollege(
            @PathVariable String id, @RequestBody @Valid CollegeRequest college) {
        if (StringUtils.isEmpty(id)) throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0002);
        return new ResponseEntity<>(collegeService.updateCollege(college, id), HttpStatus.OK);
    }

    @TrackExecutionTime
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCollege(@PathVariable(value = "id") String id) {
        if (StringUtils.isEmpty(id)) throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0002);
        collegeService.deleteCollegeById(id);
        return new ResponseEntity<>("College deleted successfully!", HttpStatus.OK);
    }

    @TrackExecutionTime
    @PostMapping(value="/upload")
    public ResponseEntity<String> uploadCollegesFile(
            @RequestParam("college_names") MultipartFile file) throws Exception{
        // if invalid file type: throw exception
        if (file.isEmpty() || !file.getContentType().equals("text/csv"))
            throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0003);

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<CollegeRequest> allCollegeBeans = new CsvToBeanBuilder<CollegeRequest>(reader)
                    .withType(CollegeRequest.class).withIgnoreLeadingWhiteSpace(true).build().parse();
            collegeService.addCollegeList(allCollegeBeans);
            return new ResponseEntity<>("File uploaded Successfully!", HttpStatus.CREATED);
        }
    }
}
