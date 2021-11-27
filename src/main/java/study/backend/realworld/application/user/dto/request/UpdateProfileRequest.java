package study.backend.realworld.application.user.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.model.UpdateProfileModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfileRequest {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
    private String bio;
    private String image;

    public UpdateProfileModel toUpdateProfileRequest() {
        return new UpdateProfileModel(
                this.username,
                this.email,
                this.password,
                this.bio,
                this.image
        );
    }
}
