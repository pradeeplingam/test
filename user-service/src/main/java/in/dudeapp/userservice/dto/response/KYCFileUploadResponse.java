package in.dudeapp.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 03/07/22
 */
@Data
@Builder
public class KYCFileUploadResponse {
    private String url;
    private Integer documentId;
}
