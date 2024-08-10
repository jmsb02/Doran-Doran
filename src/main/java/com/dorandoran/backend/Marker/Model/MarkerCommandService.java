package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import com.dorandoran.backend.File.Model.S3ImageService;
import com.dorandoran.backend.Marker.dto.MarkerCheckDTO;
import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Marker.exception.MarkerNotFoundException;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MarkerCommandService {

    private final MarkerRepository markerRepository;
    private final MemberRepository memberRepository;
    private final S3ImageService s3ImageService;
    private final FileService fileService;

    /**
     * 새로운 마커 저장
     */
    public Long saveMarker(MarkerDTO markerDTO, Member member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        List<String> imageUrls = uploadImages(markerDTO.getImageFiles());

        Marker marker = createMarker(markerDTO, findMember,imageUrls);
        return markerRepository.save(marker).getId();
    }

    /**
     * 마커 파일 업로드
     */
    private List<String> uploadImages(List<MultipartFile> imageFiles) {
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile imageFile : imageFiles) {
            if(!imageFile.isEmpty()) {
                String imageUrl = s3ImageService.upload(imageFile);
                imageUrls.add(imageUrl);
            }
        }
        return imageUrls;
    }

    /**
     * 마커 단일 조회
     */
    @Transactional(readOnly = true)
    public MarkerCheckDTO findMarkerOne(Long markerId) {
        Marker findMarker = markerRepository.findById(markerId).orElseThrow(MarkerNotFoundException::new);
        return convertToDTO(findMarker);
    }

    /**
     * 사용자가 작성한 마커 모두 조회
     */
    @Transactional(readOnly = true)
    public List<MarkerCheckDTO> findMarkersUsers(Long memberId) {
        List<Marker> Markers = markerRepository.findByMemberId(memberId);
        //마커를 하나씩 뽑아서 DTO로 변환 후 리스트에 저장
        return Markers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 마커 삭제
     */
    @Transactional
    public void deleteMarker(Long marker_id) {
        Marker findMarker = markerRepository.findById(marker_id)
                .orElseThrow(() -> new MarkerNotFoundException());

        markerRepository.delete(findMarker);

    }

    private Marker createMarker(MarkerDTO markerDTO, Member findMember, List<String> imageUrls) {
        Marker marker = Marker.builder()
                .member(findMember)
                .address(markerDTO.getAddress())
                .title(markerDTO.getTitle())
                .content(markerDTO.getContent())
                .longitude(markerDTO.getLongitude())
                .latitude(markerDTO.getLatitude())
                .build();

        for (String imageUrl : imageUrls) {
            fileService.createFile(imageUrl, marker);
        }

        return marker;
    }


    private MarkerCheckDTO convertToDTO(Marker marker) {
        return new MarkerCheckDTO(
                marker.getId(),
                marker.getMember().getId(),
                marker.getAddress(),
                marker.getContent(),
                marker.getTitle(),
                marker.getLatitude(),
                marker.getLongitude()
        );
    }
}
