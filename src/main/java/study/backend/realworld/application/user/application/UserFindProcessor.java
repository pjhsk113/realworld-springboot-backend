package study.backend.realworld.application.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.exception.UserNotFountException;
import study.backend.realworld.application.user.repository.UserRepository;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class UserFindProcessor {
    private final UserRepository userRepository;

    public User findByUserName(UserName userName) throws UserNotFountException {
        return userRepository.findByProfileUserName(userName)
                .orElseThrow(UserNotFountException::new);
    }

    public User findById(long userId) throws UserNotFountException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFountException::new);
    }
}
