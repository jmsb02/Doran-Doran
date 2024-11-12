package com.dorandoran.backend.Marker;

import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Member.domain.SimpleUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/markers")
@Slf4j
public class TestController {
    //마커 작성
    @PostMapping("/test")
    public ResponseEntity<String> createMarker(@RequestBody MarkerDTO markerDTO,
                             @AuthenticationPrincipal SimpleUserDetails userDetails) {

        if (userDetails == null) {
            log.warn("userDetails is null. Authentication may not be configured correctly.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        log.info("Title={}, Content={}, userDetails.getMemberId() = {}",
                markerDTO.getTitle(), markerDTO.getContent(), userDetails.getMemberId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> testServer() {
        log.info("Entering testServer endpoint"); // 진입 로그 추가
        String responseMessage = "Server OK !!!!!!";
        log.info("Returning response: {}", responseMessage); // 반환 전 로그 추가
        return ResponseEntity.ok("Server OK !!!!!!");
    }
}


