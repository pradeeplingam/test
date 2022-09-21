package in.dudeapp.userservice.dto.request;

import in.dudeapp.userservice.entity.constants.DocumentType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by mohan on 03/07/22
 */
@Data
@Builder
public class DocumentUploadRequest {
    @NotEmpty(message = "UserId should not be null")
    private Integer userId;
    @NotEmpty(message = "documentType should not be null")
    private DocumentType documentType;
}
