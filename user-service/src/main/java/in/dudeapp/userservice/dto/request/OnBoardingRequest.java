package in.dudeapp.userservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by mohan on 22/06/22
 */
@Data
public class OnBoardingRequest {

    @NotNull
    private boolean isOnBoard;
}
