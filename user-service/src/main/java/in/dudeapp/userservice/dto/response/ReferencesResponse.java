package in.dudeapp.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author nandhan, Created on 24/07/22
 */
@Data
@Builder
@AllArgsConstructor
public class ReferencesResponse {
    private String id;
    private String name;
    private String contact;
    private String userId;
    private String collegeId;
}
