package com.dorandoran.backend.marker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MarkerNotFoundException extends RuntimeException {

    public MarkerNotFoundException() {
        super("마커가 존재하지 않습니다.");
    }
}
