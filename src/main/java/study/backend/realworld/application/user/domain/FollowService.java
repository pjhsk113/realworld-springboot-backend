package study.backend.realworld.application.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.user.domain.exception.ExistsUserException;
import study.backend.realworld.application.user.domain.exception.UserNotFountException;

@Service
@RequiredArgsConstructor
public class FollowService {
    private UserRepository userRepository;

    public Profile follow(User user, String userName) throws UserNotFountException, ExistsUserException {
        User target = userRepository.findByUsername(userName)
                .orElseThrow(UserNotFountException::new);
        user.follow(target);
        return new Profile(userName);
    }

    public Profile unfollow(User user, String userName) throws UserNotFountException {
        User target = userRepository.findByUsername(userName)
                .orElseThrow(UserNotFountException::new);
        user.unfollow(target);
        return new Profile(userName);
    }
}
