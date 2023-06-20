package pl.ioad.skyflow.logic.exception;

import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.ioad.skyflow.logic.exception.type.*;

import javax.naming.AuthenticationException;
import javax.naming.InsufficientResourcesException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        return ResponseEntity.status(status).body(new ErrorResponse(
                (HttpStatus) status,
                ex.getClass().getSimpleName(),
                ex.getLocalizedMessage()));
    }

    @ExceptionHandler({InvalidBusinessArgumentException.class,
            ParameterException.class,
            InvalidDataException.class,
            DuplicatedDataException.class})
    public final ResponseEntity<Object> handleException(RuntimeException exception) {
        return handleResponse(HttpStatus.NOT_ACCEPTABLE, exception);
    }
    @ExceptionHandler({AuthException.class, AuthenticationException.class, InsufficientResourcesException.class,
            BadCredentialsException.class})
    public final ResponseEntity<Object> handleAuth(RuntimeException exception) {
        return handleResponse(HttpStatus.UNAUTHORIZED, exception);
    }
    @ExceptionHandler({ForbiddenException.class, InsufficientAuthenticationException.class})
    public final ResponseEntity<Object> handleForbiddenException(RuntimeException exception) {
        return handleResponse(HttpStatus.FORBIDDEN, exception);
    }
    @ExceptionHandler({EntityNotFoundException.class})
    public final ResponseEntity<Object> handleNotFound(RuntimeException exception) {
        return handleResponse(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        return handleResponse(HttpStatus.BAD_REQUEST, (RuntimeException) exception);
    }

    public final ResponseEntity<Object> handleResponse(HttpStatus httpStatus, RuntimeException exception) {
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(httpStatus,
                                                                    exception.getClass().getSimpleName(),
                                                                    exception.getMessage()));
    }
}
