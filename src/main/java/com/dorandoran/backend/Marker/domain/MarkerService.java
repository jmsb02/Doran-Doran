package com.dorandoran.backend.Marker.domain;

import com.dorandoran.backend.File.DTO.FileDTO;
import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import com.dorandoran.backend.File.exception.CustomImageException;
import com.dorandoran.backend.File.exception.ErrorCode;
import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Marker.dto.MarkerFindDTO;
import com.dorandoran.backend.Marker.exception.MarkerNotFoundException;
import com.dorandoran.backend.Member.domain.Member;
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
    public Long saveMarker(MarkerDTO markerDTO, List<MultipartFile> imageFiles, Member member) {
        log.info("saveMarker 메서드 호출");

        // 마커 생성
        Marker marker = createMarker(markerDTO, member);
        Marker savedMarker = markerRepository.save(marker);

        // 파일 생성 및 마커와 연결
        List<File> files = convertAndSaveFiles(imageFiles, savedMarker, member);
        savedMarker.setFiles(files); // 마커에 파일 설정

        return savedMarker.getId();
    }

    /**
     * 마커 단일 조회 (DTO로 반환)
     */
    @Transactional(readOnly = true)
    public MarkerFindDTO findMarkerOne(Long markerId) {
        return convertToDTO(getMarkerById(markerId));
    }

    /**
     * 사용자가 작성한 마커 모두 조회 (DTO로 반환)
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
     * 전체 마커 조회 (DTO로 반환)
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
     * 파일을 변환하고 마커 연결하는 메서드
     */
    private List<File> convertAndSaveFiles(List<MultipartFile> files, Marker marker, Member member) {
        List<File> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            String base64Image = fileService.convertToBase64(file);
            fileService.validateImage(base64Image);

            // 파일 생성 및 마커에 설정
            File resultFile = fileService.createFileWithMarker(base64Image, file.getOriginalFilename(), marker, member);
            fileList.add(resultFile);
        }
        return fileList;
    }

    private void validateFileCount(List<MultipartFile> files) {
        if (files.size() > 3) {
            throw new CustomImageException(ErrorCode.MAX_PHOTO_LIMIT_EXCEPTION, "사진은 최대 3개까지만 업로드할 수 있습니다.");
        }
    }

    /**
     * 마커 데이터 조회
     */
    private Marker getMarkerById(Long markerId) {
        return markerRepository.findById(markerId)
                .orElseThrow(MarkerNotFoundException::new);
    }

    /**
     * 객체 생성
     */
    private Marker createMarker(MarkerDTO markerDTO, Member member) {
        return Marker.builder()
                .member(member)
                .title(markerDTO.getTitle())
                .content(markerDTO.getContent())
                .address(markerDTO.getAddress())
                .build();
    }

    /**
     * 결과 변환
     */
    private MarkerFindDTO convertToDTO(Marker marker) {
        List<FileDTO> fileDTOs = marker.getFiles().stream()
                .map(file -> new FileDTO(
                        file.getOriginalFilename(),
                        file.getStoreFilename(),
                        file.getFileType(),
                        file.getBase64Data()))
                .collect(Collectors.toList());

        return new MarkerFindDTO(
                marker.getTitle(),
                marker.getContent(),
                marker.getAddress(),
                fileDTOs);
    }
}
