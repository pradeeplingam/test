package in.dudeapp.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 21/07/22
 */
@Data
@Builder
public class SelfieResponse {
    private String s3Url;
    private Integer userId;
    private String preSignedUrl;
}
