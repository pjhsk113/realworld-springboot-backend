package study.backend.realworld.application.user.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.domain.model.UserRegisterModel;

import javax.validation.constraints.NotBlank;

@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @javax.validation.constraints.Email
    private String email;

    @NotBlank
    private String password;

    public UserRegisterModel toRegisterRequest() {
        return new UserRegisterModel(
                new UserName(username),
                new Email(email),
                this.password);
    }
}
