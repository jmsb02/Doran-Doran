package com.dorandoran.backend.Marker.Controller;

import com.dorandoran.backend.Marker.Model.MarkerCommandService;
import com.dorandoran.backend.Marker.dto.MarkerCheckDTO;
import com.dorandoran.backend.Marker.dto.MarkerDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marker")
public class MarkerController {

    private final MarkerCommandService markerCommandService;

    //마커 페이지 표시
    @GetMapping("/new")
    public ResponseEntity<Void> savePost() {
        return ResponseEntity.ok().build();
    }

    //마커 작성
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody @Valid MarkerDTO markerDTO) {
        return markerCommandService.createMarker(markerDTO);
    }

    //마커 단일 조회
    @GetMapping("/{marker_id}")
    public ResponseEntity<?> findPostOne(@PathVariable("marker_id") Long marker_id) {
        //게시물 조회
        MarkerCheckDTO markerOne = markerCommandService.findMarkerOne(marker_id);

        return ResponseEntity.ok(markerOne);
    }

    //특정 사용자 마커 조회
    @GetMapping("/user/{member_id}")
    public ResponseEntity<List<MarkerCheckDTO>> findMarkersByUser(@PathVariable Long member_id) {
        List<MarkerCheckDTO> markersByUsers = markerCommandService.findMarkersByUsers(member_id);
        return ResponseEntity.ok(markersByUsers);
    }


    @DeleteMapping("/{marker_id}")
    public ResponseEntity<Map<String, Object>> deleteMarker(@PathVariable Long marker_id) {
        Map<String, Object> response = markerCommandService.deleteMarker(marker_id);
        return ResponseEntity.ok(response);
    }
}
