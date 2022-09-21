package in.dudeapp.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author nandhan, Created on 17/07/22
 */

@Data
@Builder
public class CollegeResponse {
    private List<CollegeDTO> data;
    private PageDTO page;
}