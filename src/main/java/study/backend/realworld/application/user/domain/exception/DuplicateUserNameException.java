package study.backend.realworld.application.user.domain.exception;

import javax.transaction.SystemException;

public class DuplicateUserNameException extends SystemException {
    public DuplicateUserNameException(String message) {
        super(message);
    }
}
