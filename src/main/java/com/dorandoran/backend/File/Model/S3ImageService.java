package com.dorandoran.backend.File.Model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dorandoran.backend.File.exception.CustomS3Exception;
import com.dorandoran.backend.File.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 이미지를 업로드하는 메서드
     */
    public String upload(MultipartFile image) {
        validateImage(image);
        return uploadImageToS3(image);
    }

    /**
     * S3에 이미지를 업로드하는 메서드
     */
    private String uploadImageToS3(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        String s3FileName = createS3FileName(originalFilename);

        try (InputStream is = image.getInputStream()) {
            ObjectMetadata metadata = createObjectMetadata(is.available(), originalFilename);
            uploadFileToS3(s3FileName, is, metadata);
            return getS3FileUrl(s3FileName);
        } catch (IOException e) {
            log.error("이미지 업로드 중 IO 예외 발생: {}", e.getMessage(), e);
            throw new CustomS3Exception(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD, "이미지 업로드 중 IO 예외 발생.");
        }
    }

    /**
     * S3에 업로드된 파일의 URL 생성 메서드
     */
    private String getS3FileUrl(String s3FileName) {
        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    /**
     * S3에 파일을 업로드하는 실제 작업을 수행하는 메서드
     */
    private void uploadFileToS3(String s3FileName, InputStream inputStream, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
    }

    /**
     * 업로드할 이미지의 유효성 검사
     */
    private void validateImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new CustomS3Exception(ErrorCode.EMPTY_FILE_EXCEPTION, "업로드된 파일이 없습니다.");
        }

        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null) {
            throw new CustomS3Exception(ErrorCode.EMPTY_FILE_EXCEPTION, "파일 이름이 없습니다.");
        }

        FileValidator.validateImageFileExtension(originalFilename);
    }

    /**
     * S3에 업로드할 파일의 이름을 생성하는 메서드
     */
    private String createS3FileName(String originalFilename) {
        return UUID.randomUUID().toString().substring(0, 10) + originalFilename;
    }

    /**
     * S3에 업로드할 파일의 메타데이터를 생성하는 메서드
     */
    private ObjectMetadata createObjectMetadata(int contentLength, String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        ObjectMetadata metadata = new ObjectMetadata();

        if (FileExtension.isValidExtension(extension)) {
            metadata.setContentType(FileExtension.valueOf(extension.toUpperCase()).getMimeType());
        }

        metadata.setContentLength(contentLength);
        return metadata;
    }

    /**
     * 이미지 주소를 URL 객체로 변환하는 메서드
     */
    private URL createURL(String imageAddress) {
        try {
            return new URL(imageAddress);
        } catch (MalformedURLException e) {
            log.error("이미지 주소가 유효하지 않습니다: {}", e.getMessage(), e);
            throw new CustomS3Exception(ErrorCode.INVALID_IMAGE_ADDRESS, "이미지 주소가 유효하지 않습니다.");
        }
    }

    /**
     * S3에 사용할 키를 디코딩(데이터를 원래 형태로 변환)하는 메서드
     */
    private String decodeKey(String path) {
        try {
            return URLDecoder.decode(path.substring(1), "UTF-8"); // 맨 앞의 '/' 제거
        } catch (UnsupportedEncodingException e) {
            log.error("이미지 경로 디코딩 중 오류 발생: {}", e.getMessage(), e);
            throw new CustomS3Exception(ErrorCode.INVALID_IMAGE_ADDRESS, "이미지 경로 디코딩 중 오류 발생.");
        }
    }

    /**
     * 이미지 주소에서 S3 객체키를 추출하는 메서드
     */
    private String getKeyFromImageAddress(String imageAddress) {
        URL url = createURL(imageAddress);
        return decodeKey(url.getPath());
    }

    /**
     * S3에서 이미지 삭제하는 메서드 (개별 삭제)
     */
    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        deleteS3Object(key);
    }

    /**
     * S3에서 객체를 삭제하는 메서드 (전체 삭제)
     */
    private void deleteS3Object(String key) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        } catch (AmazonServiceException e) {
            log.error("S3에서 이미지 삭제 중 오류 발생: {}", e.getMessage(), e);
            throw new CustomS3Exception(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD, "S3에서 이미지 삭제 중 오류 발생.");
        }
    }
}