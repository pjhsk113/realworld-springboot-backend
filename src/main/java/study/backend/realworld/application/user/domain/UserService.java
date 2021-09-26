package study.backend.realworld.application.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.user.domain.exception.DuplicateEmailException;
import study.backend.realworld.application.user.domain.exception.PasswordNotMatchedException;
import study.backend.realworld.application.user.domain.exception.UserNotFountException;
import study.backend.realworld.application.user.domain.model.UpdateProfileModel;
import study.backend.realworld.application.user.domain.model.UserLoginModel;
import study.backend.realworld.application.user.domain.model.UserRegisterModel;

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

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return userRepository.save(User.of(request.getUsername(),
                request.getEmail(),
                encodedPassword));
    }

    public User login(UserLoginModel request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFountException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
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
        updateUser.updateProfile(request.getUsername(), new Profile(request), user.getPassword());

        return userRepository.saveAndFlush(updateUser);

    }

}
