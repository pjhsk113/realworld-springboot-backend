package study.backend.realworld.application.user.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.model.UserRegisterModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    public UserRegisterModel toRegisterRequest() {
        return new UserRegisterModel(
                this.username,
                this.email,
                this.password);
    }
}
