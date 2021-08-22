package study.backend.realworld.application.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.SystemException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PasswordNotMatchedException extends SystemException {
    public PasswordNotMatchedException() {
        super("password not matched");
    }
}
