package study.backend.realworld.application.user.domain.exception;

import javax.transaction.SystemException;

public class ExistsUserException extends SystemException {
    public ExistsUserException() {
        super("exists follow user");
    }
}
