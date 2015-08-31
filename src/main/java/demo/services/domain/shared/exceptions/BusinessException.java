package demo.services.domain.shared.exceptions;

public class BusinessException extends ServiceException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
