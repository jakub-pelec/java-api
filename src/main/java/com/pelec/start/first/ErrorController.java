package com.pelec.start.first;

import org.springframework.http.ResponseEntity;

public class ErrorController {
    private final String error;
    public ErrorController(String error) {
        this.error = error;
    }
    public ResponseEntity<String> sendError(ResponseEntity response) {
        return response.badRequest().body(this.error);
    }
}
