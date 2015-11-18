package demo;

import demo.rest.resources.ErrorResource;
import demo.services.domain.shared.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UnknownResourceException.class)
    @ResponseBody
    public ErrorResource handleUnknownResource(UnknownResourceException e) {
        LOGGER.info(e.getIdentifier() + " " + e.getErrorCode().name() + " " + e.getMessage(), e);
        return new ErrorResource(e.getIdentifier(), localizeMessage(e));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ErrorResource handleBusinessException(BusinessException e) {
        LOGGER.info(e.getIdentifier() + " " + e.getErrorCode().name() + " " + e.getMessage(), e);
        return new ErrorResource(e.getIdentifier(), localizeMessage(e));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ErrorResource handleTechnicalException(TechnicalException e) {
        LOGGER.error(e.getIdentifier() + " " + e.getErrorCode().name() + " " + e.getMessage(), e);
        return new ErrorResource(e.getIdentifier(), localizeMessage(e));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResource handleUnknownException(Exception e) {
        final TechnicalException technicalException = new TechnicalException(ErrorCode.SERVICE_UNAVAILABLE);
        LOGGER.error(technicalException.getIdentifier() + " " + technicalException.getErrorCode().name() + " " + e.getMessage(), e);
        return new ErrorResource(technicalException.getIdentifier(), localizeMessage(technicalException));
    }

    private String localizeMessage(ServiceException e) {
        return messageSource.getMessage(e.getErrorCode().name(), new Object[]{}, Locale.getDefault());
    }
}
