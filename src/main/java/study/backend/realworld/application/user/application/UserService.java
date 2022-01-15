package study.backend.realworld.application.user.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.user.domain.Password;
import study.backend.realworld.application.user.domain.UserEntity;
import study.backend.realworld.application.user.domain.model.UpdateProfileModel;
import study.backend.realworld.application.user.domain.model.UserLoginModel;
import study.backend.realworld.application.user.domain.model.UserRegisterModel;
import study.backend.realworld.application.user.exception.DuplicateEmailException;
import study.backend.realworld.application.user.exception.PasswordNotMatchedException;
import study.backend.realworld.application.user.exception.UserNotFountException;
import study.backend.realworld.application.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity register(UserRegisterModel request) throws Exception {
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new DuplicateEmailException("duplicate email");
        }

        Password encodedPassword = Password.of(request.getPassword(), passwordEncoder);
        return userRepository.save(UserEntity.of(request.getEmail(),
                request.getUsername(),
                encodedPassword));
    }

    public UserEntity login(UserLoginModel request) throws Exception {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFountException::new);

        if (!user.matchesPassword(request.getPassword(), passwordEncoder)) {
            throw new PasswordNotMatchedException();
        }

        return user;
    }

    public UserEntity find(UserEntity user) throws UserNotFountException {
        return userRepository.findById(user.getId())
                .orElseThrow(UserNotFountException::new);
    }

    public UserEntity updateProfile(UserEntity user, UpdateProfileModel request) throws Exception {
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new DuplicateEmailException("duplicate email");
        }

        UserEntity updateUser = userRepository.findById(user.getId()).orElseThrow(UserNotFountException::new);
        updateUser.changeEmail(request.getEmail());
        updateUser.changeName(request.getUsername());
        updateUser.changePassword(Password.of(request.getPassword(), passwordEncoder));

        return userRepository.saveAndFlush(updateUser);

    }

}
