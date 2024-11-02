package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import com.dorandoran.backend.File.exception.CustomImageException;
import com.dorandoran.backend.File.exception.ErrorCode;
import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Marker.dto.MarkerFindDTO;
import com.dorandoran.backend.Marker.exception.MarkerNotFoundException;
import com.dorandoran.backend.Member.domain.Address;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MarkerService {

    private final MarkerRepository markerRepository;
    private final FileService fileService;

    /**
     * 새로운 마커 저장
     */
    public Long saveMarker(MarkerDTO markerDTO, Member member){
        log.info("saveMarker 메서드 호출");

        // MultipartFile -> base64기반 File
        List<MultipartFile> files = markerDTO.getImageFiles();

        List<File> resultFiles = convertFiles(files);

        // 마커 생성
        Marker marker = createMarker(markerDTO, member, resultFiles);

        Marker savedMarker = markerRepository.save(marker);
        log.info("Saved marker ID: {}", savedMarker.getId()); // 저장된 마커 ID 로그

        return savedMarker.getId();
    }

    /**
     * 마커 단일 조회
     */
    @Transactional(readOnly = true)
    public MarkerFindDTO findMarkerOne(Long markerId) {
        return convertToDTO(getMarkerById(markerId));
    }

    /**
     * 사용자가 작성한 마커 모두 조회
     */
    @Transactional(readOnly = true)
    public List<MarkerFindDTO> findMarkersUsers(Long memberId) {
        List<Marker> markers = markerRepository.findByMemberId(memberId);
        //마커를 하나씩 뽑아서 DTO로 변환 후 리스트에 저장
        return markers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 전체 마커 조회
     */
    @Transactional(readOnly = true)
    public List<MarkerFindDTO> findAllMarkers() {
        List<Marker> markers = markerRepository.findAll();
        return markers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 마커 삭제
     */
    @Transactional
    public void deleteMarker(Long markerId) {
        Marker findMarker = getMarkerById(markerId);
        markerRepository.delete(findMarker);
    }

    /**
     * 마커 파일 업로드
     */
    public List<File> convertFiles(List<MultipartFile> files) {

        validateFileCount(files);
        List<File> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            String base64Image = fileService.convertToBase64(file); // Base64로 변환
            fileService.validateImage(base64Image); // 유효성 검사

            //base64형식의 파일 반환
            File resultFile = new File(
                    file.getOriginalFilename(),
                    fileService.generateFileName(),
                    file.getSize(),
                    file.getContentType(),
                    base64Image
            );
            fileList.add(resultFile);
        }
        return fileList;
    }

    /**
     * 유효성 검사
     */
    private void validateMember(Member member) {
        if (member.getId() == null) {
            throw new MemberNotFoundException("회원을 찾을 수 없습니다.");
        }
    }

    private void validateFileCount(List<MultipartFile> files) {
        if (files.size() > 3) {
            throw new CustomImageException(ErrorCode.MAX_PHOTO_LIMIT_EXCEPTION, "사진은 최대 3개까지만 업로드할 수 있습니다.");
        }
    }

    /**
     * 데이터 조회
     */
    private Marker getMarkerById(Long markerId) {
        return markerRepository.findById(markerId)
                .orElseThrow(MarkerNotFoundException::new);
    }

    /**
     * 객체 생성
     */
    private Marker createMarker(MarkerDTO markerDTO, Member findMember, List<File> files) {

        Address address = findMember.getAddress();

        return Marker.builder()
                .member(findMember)
                .title(markerDTO.getTitle())
                .content(markerDTO.getContent())
                .address(address)
                .files(files)
                .build();
    }

    /**
     * 결과 변환
     */
    private MarkerFindDTO convertToDTO(Marker marker) {

        return new MarkerFindDTO(
                marker.getTitle(),
                marker.getContent(),
                marker.getAddress(),
                marker.getFiles()
        );
    }
}
