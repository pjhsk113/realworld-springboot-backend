package study.backend.realworld.application.user.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.model.UserLoginModel;

import javax.validation.constraints.NotBlank;

@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    @javax.validation.constraints.Email
    private String email;
    @NotBlank
    private String password;

    public UserLoginModel toLoginRequest() {
        return new UserLoginModel(
                new Email(this.email),
                this.password
        );
    }
}
