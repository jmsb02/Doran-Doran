package com.dorandoran.backend.Marker.exception;

public class MarkerNotFoundException extends RuntimeException {

    public MarkerNotFoundException() {
        super("마커가 존재하지 않습니다.");
    }
}
