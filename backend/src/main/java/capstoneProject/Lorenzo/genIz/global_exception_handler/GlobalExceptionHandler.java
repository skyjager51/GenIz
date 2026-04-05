package capstoneProject.Lorenzo.genIz.global_exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import capstoneProject.Lorenzo.genIz.DTO.ErrorResponseDto;
import jakarta.persistence.NoResultException;

//global exception handler to manage the exceptions thrown by the code
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e){
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e){
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResultException(NoResultException e){
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleUnexpected(MethodArgumentTypeMismatchException ex) {
        ErrorResponseDto error = new ErrorResponseDto("The argument passed is not correct.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorResponseDto> handleUnexpected(Exception ex) {
    //     ErrorResponseDto error = new ErrorResponseDto("An unexpected error occurred. Please try again later.");
    //     return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
}
