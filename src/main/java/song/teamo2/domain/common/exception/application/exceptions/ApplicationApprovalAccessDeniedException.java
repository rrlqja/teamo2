package song.teamo2.domain.common.exception.application.exceptions;

import song.teamo2.domain.common.exception.application.ApplicationException;

public class ApplicationApprovalAccessDeniedException extends ApplicationException {
    public ApplicationApprovalAccessDeniedException() {
        super("Application Approval Access Denied Exception");
    }

    public ApplicationApprovalAccessDeniedException(String message) {
        super(message);
    }
}
