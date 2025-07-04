package br.com.oel.downlink.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

// a anotacao do RestControllerAdvice faz com que seja aplicado globalmnmte para todos os controllers
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Pega todas as exceções
    public ResponseEntity<String> handleGeneralException(Exception ex){
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }

    // lida com um arquivo nao achado no download
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(FileNotFoundException ex) {
        log.warn("File not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("File not found: " + ex.getMessage());
    }


}
