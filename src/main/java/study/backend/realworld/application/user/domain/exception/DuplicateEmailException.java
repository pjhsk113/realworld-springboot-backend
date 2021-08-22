package study.backend.realworld.application.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.SystemException;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends SystemException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
