package in.dudeapp.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import in.dudeapp.userservice.entity.constants.UserType;
import lombok.Data;
import org.checkerframework.checker.regex.qual.Regex;

import javax.annotation.RegEx;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by mohan on 20/07/22
 */
@Data
public class UpdateUserRequest {

    @NotEmpty
    @Size(min = 2, max = 30, message = "firstName should not be empty")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 30, message = "lastName should not be empty")
    private String lastName;
    @NotNull
    private UserType userType;
    @NotEmpty
    @Size(min = 2, max = 30, message = "motherName should not be empty")
    private String motherName;

    @Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]{1}", message = "PanNumber is invalid")
    private String panCardNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate graduationDate;

    private String parentsOccupation;

    private String stay;

}
