package study.backend.realworld.application.user.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.model.UserLoginModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @Email
    private String email;
    @NotBlank
    private String password;

    public UserLoginModel toLoginRequest() {
        return new UserLoginModel(
                this.email,
                this.password
        );
    }
}
