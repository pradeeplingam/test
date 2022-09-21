package in.dudeapp.common.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 11/06/22
 */
@Builder
@Data
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
}
