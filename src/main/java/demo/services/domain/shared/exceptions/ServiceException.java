package demo.services.domain.shared.exceptions;

import java.util.UUID;

public abstract class ServiceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String identifier;


    protected ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        identifier = generateIdentifier();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getIdentifier() {
        return identifier;
    }

    private String generateIdentifier() {
        return UUID.randomUUID().toString();
    }
}
