package in.dudeapp.collegeservice.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import in.dudeapp.collegeservice.dto.request.CollegeRequest;
import in.dudeapp.collegeservice.dto.response.CollegeDTO;
import in.dudeapp.collegeservice.dto.response.CollegeResponse;
import in.dudeapp.collegeservice.exception.CollegeException;
import in.dudeapp.collegeservice.exception.ErrorMessage;
import in.dudeapp.collegeservice.service.CollegeService;
import in.dudeapp.common.annotation.TrackExecutionTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

/**
 * Created by mohan on 11/06/22
 */

@RestController
@AllArgsConstructor
@Slf4j
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
    public ResponseEntity<CollegeDTO> getCollegeById(@PathVariable Integer id) {
        if (id == null || id < 0) throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0002);
        CollegeDTO college = collegeService.getCollegeById(id);
        return new ResponseEntity<>(college, HttpStatus.OK);
    }

    @TrackExecutionTime
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCollege(
            @RequestBody @Valid CollegeRequest college){
        collegeService.addCollege(college);
        return new ResponseEntity<>("College is Added!", HttpStatus.CREATED);
    }

    @TrackExecutionTime
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCollege(
            @PathVariable Integer id, @RequestBody @Valid CollegeRequest college) {
        if (id == null || id < 0) throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0002); // log error
        collegeService.updateCollege(college, id);
        return new ResponseEntity<>("College is Updated!", HttpStatus.OK);
    }

    @TrackExecutionTime
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCollege(@PathVariable(value = "id") Integer id) {
        if (id == null || id < 0) throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0002);
        collegeService.deleteCollegeById(id);
        return new ResponseEntity<>("College is Deleted!", HttpStatus.OK);
    }

    @TrackExecutionTime
    @PostMapping(value="/upload")
    public ResponseEntity<String> uploadCollegesFile(
            @RequestParam("college_names") MultipartFile file) throws Exception{
        if (file.isEmpty() || !file.getContentType().equals("text/csv")) // if invalid file type: throw exception
            throw new CollegeException(ErrorMessage.COLLEGE_ERROR_CODE_0003);

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<CollegeRequest> allCollegeBeans = new CsvToBeanBuilder<CollegeRequest>(reader)
                    .withType(CollegeRequest.class).withIgnoreLeadingWhiteSpace(true).build().parse();
            collegeService.addCollegeList(allCollegeBeans);
            //log.info("");
            return new ResponseEntity<>("File Uploaded!", HttpStatus.CREATED);
        }
    }
}
