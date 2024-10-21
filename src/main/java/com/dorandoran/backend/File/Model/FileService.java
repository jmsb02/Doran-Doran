package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.File.exception.CustomImageException;
import com.dorandoran.backend.File.exception.ErrorCode;
import com.dorandoran.backend.File.exception.FileMissingException;
import com.dorandoran.backend.Marker.Model.Marker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Getter
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 파일 생성
     */
    public File createFile(String base64Image, String originalFileName) {
        validateImage(base64Image);
        String fileName = generateFileName(); //파일 이름 생성
        Long fileSize = calculateFileSize(base64Image);
        String fileType = getFileType(base64Image);

        File file = new File(originalFileName, fileName, fileSize, fileType, base64Image); // Base64 데이터 포함

        return fileRepository.save(file);
    }

    /**
     * 파일 아이디로 조회
     */
    public File getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new FileMissingException("파일이 존재하지 않습니다. 파일 아이디 : " + id));
    }

    /**
     * 파일 수정 - 메모리 문제로 파일을 직접 받을 때는 MultipartFile로 처리
     */
    public File updateFile(Long id, String newFileName, MultipartFile newFile) {
        File findFile = getFileById(id);

        //새로운 파일의 Base64 데이터로 변환
        String newBase64Image = convertToBase64(newFile);

        //Base64 이미지 검증
        validateImage(newBase64Image);

        //파일 정보 업데이트
        updateFileInfo(newFileName, findFile, newBase64Image);

        return fileRepository.save(findFile); // 변경된 파일 저장
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(Long id) {
        File findFile = getFileById(id); // 파일 조회
        // 데이터베이스에서 파일 삭제
        fileRepository.delete(findFile);
    }


    private void updateFileInfo(String newFileName, File findFile, String newBase64Image) {
        fileSet(newFileName, findFile, newBase64Image);
    }

    private void fileSet(String newFileName, File findFile, String newBase64Image) {
        findFile.setStoreFilename(newFileName);
        findFile.setBase64Data(newBase64Image);
        findFile.setFileSize(calculateFileSize(newBase64Image));
        findFile.setFileType(getFileType(newBase64Image));
    }

    private String convertToBase64(MultipartFile file) {
        //MultiFile -> Base64
        try {
            byte[] bytes = file.getBytes();
            return "data:" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new CustomImageException(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD, e.getMessage());
        }
    }


    /**
     * 파일 검증
     */
    public void validateImage(String base64Image) {

        //파일 유효성 검사
        if (base64Image == null || base64Image.isEmpty()) {
            throw new CustomImageException(ErrorCode.EMPTY_FILE_EXCEPTION, "업로드된 파일이 없습니다.");
        }

        //Base64 형식 체크
        String[] parts = base64Image.split(",");
        if (parts.length != 2) {
            throw new CustomImageException(ErrorCode.INVALID_BASE64_FORMAT, "잘못된 Base64 형식입니다. 데이터 부분이 누락되었거나 잘못된 형식입니다.");
        }
        if (!parts[0].startsWith("data:image/")) {
            throw new CustomImageException(ErrorCode.INVALID_BASE64_FORMAT, "잘못된 Base64 형식입니다.");
        }

        //파일 형식 체크
        String mineType = parts[0].split(";")[0].substring("data:".length());
        List<String> allowTypes = List.of("image/jpeg", "image/png", "image/jpg");
        if (!allowTypes.contains(mineType)) {
            throw new CustomImageException(ErrorCode.UNSUPPORTED_IMAGE_TYPE, "허용되지 않는 파일 형식입니다.");
        }

        //Base64 데이터 부분 추출
        String base64Data = parts[1];

        //Base64 데이터 크기 체크
        byte[] decodeBytes = Base64.getDecoder().decode(base64Data);
        if (decodeBytes.length > 5 * 1024 * 1024) { //5MB 이상 X
            throw new CustomImageException(ErrorCode.FILE_SIZE_EXCEPTION, "파일 크기는 5MB를 초과할 수 없습니다.");
        }
    }


    /**
     * 파일 이름 생성 (무작위)
     */
    private String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 파일 원본 크기 반환
     */
    private Long calculateFileSize(String base64Image) {
        String[] parts = base64Image.split(",");
        String base64Data = parts[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        return (long) decodedBytes.length;
    }

    /**
     * 파일 형식 반환
     */
    private String getFileType(String base64Image) {
        String[] parts = base64Image.split(",");
        return parts[0].split(";")[0].substring("data:".length()); //파일 타입 반환
    }

}
