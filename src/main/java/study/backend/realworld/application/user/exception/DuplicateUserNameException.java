package study.backend.realworld.application.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.SystemException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUserNameException extends SystemException {
    public DuplicateUserNameException(String message) {
        super(message);
    }
}
