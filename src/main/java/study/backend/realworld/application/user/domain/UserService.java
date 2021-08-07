package study.backend.realworld.application.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public User register(UserRegisterModel request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return userRepository.save(User.of(request.getUsername(),
                request.getEmail(),
                encodedPassword));
    }

    public User login(UserLoginModel request) {
        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("email not found"));
    }

    public User find(User user) {
        return userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("user not found"));
    }
}
