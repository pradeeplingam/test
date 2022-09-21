package in.dudeapp.userservice.dto.request;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author nandhan, Created on 17/07/22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollegeRequest {

    @CsvBindByName(column = "College Name")
    @NonNull
    @Size(min = 3, max = 50, message = "Name should be in between 3 and 50 Characters long.")
    private String name;

    @CsvBindByName(column = "Address Line 1")
    @NonNull
    @Size(min = 3, max = 50, message = "Name should be in between 3 and 50 Characters long.")
    private String street;

    @CsvBindByName(column = "City")
    @NonNull
    @Size(min = 3, max = 50, message = "City should be in between 3 and 50 Characters long.")
    private String city;

    @CsvBindByName(column = "District")
    @NonNull
    @Size(min = 3, max = 50, message = "District should be in between 3 and 50 Characters long.")
    private String district;

    @CsvBindByName(column = "State")
    @NonNull
    @Size(min = 3, max = 20, message = "State should be in between 3 and 20 Characters long.")
    private String state;

    @CsvBindByName(column = "Pincode")
    @NonNull
    @Min(100)
    private int pincode;
}