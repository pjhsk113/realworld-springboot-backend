package study.backend.realworld.application.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findByEmail(Email email);
    Optional<User> findByProfileUserName(UserName username);
}
