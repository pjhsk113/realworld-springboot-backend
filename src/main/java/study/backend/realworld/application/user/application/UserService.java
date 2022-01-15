package study.backend.realworld.application.user.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.user.domain.Password;
import study.backend.realworld.application.user.domain.User;
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

    public User register(UserRegisterModel request) throws Exception {
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new DuplicateEmailException("duplicate email");
        }

        Password encodedPassword = Password.of(request.getPassword(), passwordEncoder);
        return userRepository.save(User.of(request.getEmail(),
                request.getUsername(),
                encodedPassword));
    }

    public User login(UserLoginModel request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFountException::new);

        if (!user.matchesPassword(request.getPassword(), passwordEncoder)) {
            throw new PasswordNotMatchedException();
        }

        return user;
    }

    public User find(User user) throws UserNotFountException {
        return userRepository.findById(user.getId())
                .orElseThrow(UserNotFountException::new);
    }

    public User updateProfile(User user, UpdateProfileModel request) throws Exception {
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new DuplicateEmailException("duplicate email");
        }

        User updateUser = userRepository.findById(user.getId()).orElseThrow(UserNotFountException::new);
        updateUser.changeEmail(request.getEmail());
        updateUser.changeName(request.getUsername());
        updateUser.changePassword(Password.of(request.getPassword(), passwordEncoder));

        return userRepository.saveAndFlush(updateUser);

    }

}
