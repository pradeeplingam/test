package in.dudeapp.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author nandhan, Created on 17/07/22
 */


@Data
@Builder
public class PageDTO {
    private int size;
    private int pageNumber;
    private long totalResults;
}
