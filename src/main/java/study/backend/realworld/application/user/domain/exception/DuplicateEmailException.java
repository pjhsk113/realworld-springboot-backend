package study.backend.realworld.application.user.domain.exception;

import javax.transaction.SystemException;

public class DuplicateEmailException extends SystemException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
