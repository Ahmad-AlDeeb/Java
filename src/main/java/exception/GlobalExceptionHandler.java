package exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import util.ApiResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static util.ApiResponseCreator.createUnifiedResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handle(Exception exception) {
        return createUnifiedResponse(INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null);
    }
}
