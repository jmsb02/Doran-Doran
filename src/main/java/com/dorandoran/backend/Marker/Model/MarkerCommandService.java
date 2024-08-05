package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.Marker.dto.MarkerCheckDTO;
import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Marker.exception.MarkerNotFoundException;
import com.dorandoran.backend.Marker.exception.MarkerServiceException;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MarkerCommandService {

    private final MarkerRepository markerRepository;
    private final MemberRepository memberRepository;

    /**
     * 새로운 마커 저장
     */
    public Long saveMarker(MarkerDTO markerDTO) {

        //Member 조회
        Member findMember = memberRepository.findById(markerDTO.getMember_id())
                .orElseThrow(() -> new MemberNotFoundException());

        //마커 생성 및 저장
        Marker marker = new Marker(findMember, markerDTO.getName(), markerDTO.getLatitude(), markerDTO.getLongitude());
        Marker savedMarker = markerRepository.save(marker);

        //저장된 마커의 ID 반환
        return savedMarker.getId();
    }

    /**
     * 마커 저장 시 응답 API 처리 부분
     */
    public ResponseEntity<Map<String, Object>> createMarker(MarkerDTO MarkerDTO) {
        Map<String,Object> response = new HashMap<>();
        try {
            Long marker_id = saveMarker(MarkerDTO);
            response.put("success", true);
            response.put("post_id", marker_id);
            return ResponseEntity.ok(response);
        } catch (MarkerNotFoundException e) {
            response.put("success", false);
            response.put("error", "작성 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "작성 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 마커 단일 조회
     */
    public MarkerCheckDTO findMarkerOne(Long marker_id) {
        try {
            Marker findMarker = markerRepository.findById(marker_id).orElseThrow(() -> new MarkerNotFoundException());
            return convertToDTO(findMarker);
        } catch (Exception e) {
            throw new MarkerServiceException("마커 조회 중 오류 발생", e); // 사용자 정의 예외 발생
        }
    }

    /**
     * 사용자가 작성한 마커 모두 조회
     */
    public List<MarkerCheckDTO> findMarkersByUsers(Long member_id) {
        List<Marker> Markers = markerRepository.findByAuthorId(member_id);
        //마커를 하나씩 뽑아서 DTO로 변환 후 리스트에 저장
        return Markers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 마커 삭제
     */
    @Transactional
    public Map<String, Object> deleteMarker(Long marker_id) {
        Map<String, Object> response = new HashMap<>();
        Marker findMarker = markerRepository.findById(marker_id)
                .orElseThrow(() -> new MarkerNotFoundException());

        markerRepository.delete(findMarker);

        response.put("success", true);
        return response;
    }

    private MarkerCheckDTO convertToDTO(Marker marker) {
        return new MarkerCheckDTO(
                marker.getId(),
                marker.getAuthor().getId(),
                marker.getLatitude(),
                marker.getLongitude(),
                marker.getName());
    }
}
