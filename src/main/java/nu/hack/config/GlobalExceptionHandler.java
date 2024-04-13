package nu.hack.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nu.hack.common.dto.ErrorResponse;
import nu.hack.common.exception.CommonException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is responsible for configuring responses when exception is caught.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler
    public ErrorResponse handleLocalizedError(CommonException exception, HttpServletResponse response) {
        log.error("Caught exception '{}'.\n {}", exception.getMessage(), exception.getStackTrace());

        response.setStatus(exception.getStatus());
        return new ErrorResponse(exception.getMessage(), HttpStatusCode.valueOf(exception.getStatus()));
    }

    /**
     * Exception handler for annotation based(NotNull, NotBlank) validation.
     * <p>Converts the exception message to the format: {error field name} + ' ' + {localized error message}</p>
     *
     * @param exception {@link MethodArgumentNotValidException}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgument(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        String firstError = exception.getBindingResult().getAllErrors()
                .stream()
                .map(err -> err.unwrap(ConstraintViolation.class))
                .map(err -> err.getPropertyPath() + " " + err.getMessage())
                .findFirst()
                .orElse(null);

        ErrorResponse body = new ErrorResponse(firstError, HttpStatusCode.valueOf(400));
        return ResponseEntity.status(400).body(body);
    }

}
