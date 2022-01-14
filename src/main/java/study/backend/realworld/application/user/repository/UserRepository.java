package study.backend.realworld.application.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.UserEntity;
import study.backend.realworld.application.user.domain.UserName;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findByEmail(Email email);
    Optional<UserEntity> findByProfileUsername(UserName username);
}
