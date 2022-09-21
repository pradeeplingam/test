package in.dudeapp.userservice.dto.response.lambda;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mohan on 04/07/22
 */
@Data
public class PanCardData {
    private String name;
    private String fatherName;
    private String dob;
    private String pan;
    private String type;
}
