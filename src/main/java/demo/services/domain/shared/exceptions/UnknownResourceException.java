package demo.services.domain.shared.exceptions;

public class UnknownResourceException extends BusinessException {
    public UnknownResourceException() {
        super(ErrorCode.UNKNOWN_RESOURCE);
    }
}
