package demo.services.domain.shared.exceptions;

public class TechnicalException extends ServiceException {
    public TechnicalException(ErrorCode errorCode) {
        super(errorCode);
    }
}
