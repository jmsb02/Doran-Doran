package com.dorandoran.backend.Marker.Controller;

import com.dorandoran.backend.Marker.Model.MarkerCommandService;
import com.dorandoran.backend.Marker.dto.DeleteMarkerResponseDTO;
import com.dorandoran.backend.Marker.dto.MarkerCheckDTO;
import com.dorandoran.backend.Marker.dto.MarkerDTO;

import com.dorandoran.backend.Marker.dto.MarkerResponseDTO;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
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
    private final MemberRepository memberRepository;

    //마커 작성
    @PostMapping
    public ResponseEntity<MarkerResponseDTO> createPost(@RequestBody @Valid MarkerDTO markerDTO) {
        Member member = memberRepository.findById(markerDTO.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Long markerId = markerCommandService.saveMarker(markerDTO, member);
        MarkerResponseDTO response = new MarkerResponseDTO(markerId, "마커가 성공적으로 생성되었습니다.");
        return ResponseEntity.ok(response);
    }

    //마커 단일 조회
    @GetMapping("/{marker_id}")
    public ResponseEntity<MarkerCheckDTO> findPostOne(@PathVariable("marker_id") Long marker_id) {
        //게시물 조회
        MarkerCheckDTO marker = markerCommandService.findMarkerOne(marker_id);

        return ResponseEntity.ok(marker);
    }

    //특정 사용자 마커 조회
    @GetMapping("/user/{member_id}")
    public ResponseEntity<List<MarkerCheckDTO>> findMarkersByUser(@PathVariable Long member_id) {
        List<MarkerCheckDTO> markers = markerCommandService.findMarkersUsers(member_id);
        return ResponseEntity.ok(markers);
    }

    //전체 마커 조회
    @GetMapping
    public ResponseEntity<List<MarkerCheckDTO>> findAllMarkers() {
        List<MarkerCheckDTO> markers = markerCommandService.findAllMarkers();
        return ResponseEntity.ok(markers);
    }


    @DeleteMapping("/{marker_id}")
    public ResponseEntity<DeleteMarkerResponseDTO> deleteMarker(@PathVariable Long marker_id) {
        markerCommandService.deleteMarker(marker_id);
        DeleteMarkerResponseDTO response = new DeleteMarkerResponseDTO("마커가 삭제되었습니다.", true);
        return ResponseEntity.ok(response);
    }
}
