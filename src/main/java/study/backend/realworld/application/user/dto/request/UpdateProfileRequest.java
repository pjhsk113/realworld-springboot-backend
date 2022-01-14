package study.backend.realworld.application.user.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.Image;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.domain.model.UpdateProfileModel;

import javax.validation.constraints.NotBlank;

@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfileRequest {
    @NotBlank
    private String username;
    @javax.validation.constraints.Email
    private String email;
    @NotBlank
    private String password;
    private String bio;
    private String image;

    public UpdateProfileModel toUpdateProfileRequest() {
        return new UpdateProfileModel(
                new UserName(this.username),
                new Email(this.email),
                this.password,
                this.bio,
                new Image(this.image)
        );
    }
}
