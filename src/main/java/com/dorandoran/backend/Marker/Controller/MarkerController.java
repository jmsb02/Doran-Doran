package com.dorandoran.backend.Marker.Controller;

import com.dorandoran.backend.Marker.Model.MarkerService;
import com.dorandoran.backend.Marker.dto.*;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.SimpleUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/markers")
@Slf4j
public class MarkerController {

    private final MarkerService markerService;

    //마커 작성
    @PostMapping
    public ResponseEntity<MarkerFindDTO> createMarker(@RequestPart("markerDTO") @Valid MarkerDTO markerDTO,
                                             @RequestPart("files") List<MultipartFile> files,
                                             @AuthenticationPrincipal SimpleUserDetails userDetails) {

        Member findMember = userDetails.getMember();
        Long markerId = markerService.saveMarker(markerDTO, files, findMember);
        MarkerFindDTO markerFindDTO = markerService.findMarkerOne(markerId);
        return new ResponseEntity<>(markerFindDTO, HttpStatus.CREATED);
    }

    //마커 단일 조회
    @GetMapping("/{marker_id}")
    public ResponseEntity<MarkerFindDTO> findPostOne(@PathVariable("marker_id") Long markerId) {
        MarkerFindDTO marker = markerService.findMarkerOne(markerId);
        return ResponseEntity.ok(marker);
    }

    //특정 사용자 마커 조회
    @GetMapping("/member/{member_id}")
    public ResponseEntity<List<MarkerFindDTO>> findMarkersByUser(@PathVariable("member_id") Long memberId) {
        List<MarkerFindDTO> markers = markerService.findMarkersUsers(memberId);
        return ResponseEntity.ok(markers);
    }

    //전체 마커 조회
    @GetMapping("/allMarkers")
    public ResponseEntity<List<MarkerFindDTO>> findAllMarkers() {
        List<MarkerFindDTO> markers = markerService.findAllMarkers();
        return ResponseEntity.ok(markers);
    }

    //마커 삭제
    @DeleteMapping("/{marker_id}")
    public ResponseEntity<Void> deleteMarker(@PathVariable("marker_id") Long markerId) {
        markerService.deleteMarker(markerId);
        return ResponseEntity.noContent().build();
    }
}
