package in.dudeapp.userservice.controller;

import in.dudeapp.common.annotation.TrackExecutionTime;
import in.dudeapp.userservice.constant.ErrorMessage;
import in.dudeapp.userservice.dto.request.ReferencesRequest;
import in.dudeapp.userservice.dto.response.ReferencesResponse;
import in.dudeapp.userservice.exception.UserException;
import in.dudeapp.userservice.service.ReferencesService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author nandhan, Created on 23/07/22
 */
@AllArgsConstructor
@RestController
@RequestMapping("/references/")
public class ReferenceController {

    private ReferencesService referencesService;

    //TODO add logging

    @TrackExecutionTime
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addReferences(@RequestBody List<ReferencesRequest> allReferences) {
        if (allReferences.isEmpty())
            throw new UserException(ErrorMessage.GENERAL_ERROR_CODE_0001);
        referencesService.addReferences(allReferences);
        return new ResponseEntity<>("References added succesfully!", HttpStatus.CREATED);
    }

    @TrackExecutionTime
    @GetMapping(value = "referenceid/{id}",produces = APPLICATION_JSON_VALUE)
    public ReferencesResponse getReferenceByReferenceId(@PathVariable String id) {
        if (id.isEmpty())
            throw new UserException(ErrorMessage.REFERENCES_ERROR_CODE_0002);
        return referencesService.getReferencesByReferenceId(id);
    }

    @TrackExecutionTime
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<ReferencesResponse> getReferences(
            @RequestParam(value = "userId",required = false) String userId,
            @RequestParam(value = "collegeId", required = false) String collegeId) {
        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(collegeId))
            throw new UserException(ErrorMessage.REFERENCES_ERROR_CODE_0002);
        if (StringUtils.isEmpty(userId))
            return referencesService.getReferencesByCollegeId(collegeId);
        if (StringUtils.isEmpty(collegeId))
            return referencesService.getReferencesByUserId(userId);
        return referencesService.getReferencesByUserIdAndCollegeId(userId, collegeId);
    }

    @TrackExecutionTime
    @DeleteMapping(value = "{userId}", produces = APPLICATION_JSON_VALUE)
    public String deleteReferencesByUserId(@PathVariable String userId) {
        if(userId.isEmpty())
            throw new UserException(ErrorMessage.REFERENCES_ERROR_CODE_0002);
        referencesService.deleteReferences(userId);
        return "Reference deleted successfully!";
    }

    @TrackExecutionTime
    @DeleteMapping(value = "list", produces = APPLICATION_JSON_VALUE)
    public String deleteReferencesWithList(@RequestBody List<String> allIds) {
        if (allIds.isEmpty())
            throw new UserException(ErrorMessage.GENERAL_ERROR_CODE_0001);
        referencesService.deleteReferencesWithList(allIds);
        return "References list deleted successfully";
    }

    /*
    @TrackExecutionTime
    @PutMapping("{userId}")
    public String updateReferencesByUserID(@PathVariable String userId, @RequestBody List<ReferencesRequest> allContacts) {
        // TODO UPDATE CONTACTS BY USER ID.
        return null;
    }
     */
}