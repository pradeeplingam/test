package in.dudeapp.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author nandhan, Created on 17/07/22
 */

@Data
@Builder
public class CollegeDTO {
    private String id;
    private String name;
    private String street;
    private String city;
    private String district;
    private String state;
    private int pincode;
}
