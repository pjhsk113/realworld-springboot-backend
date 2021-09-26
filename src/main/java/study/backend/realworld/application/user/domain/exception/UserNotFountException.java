package study.backend.realworld.application.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.SystemException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFountException extends SystemException {
    public UserNotFountException() {
        super("member not found");
    }
}
