package in.dudeapp.userservice.dto.request;

import lombok.Data;

/**
 * Created by mohan on 11/06/22
 */
@Data
public class SaveCollegeRequest {
    private String name;
    private String street;
    private String zip;
    private String state;
    private String district;
}
