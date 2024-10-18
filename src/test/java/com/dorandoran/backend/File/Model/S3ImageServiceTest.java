package com.dorandoran.backend.File.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.dorandoran.backend.File.exception.CustomImageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URL;

public class S3ImageServiceTest {

    @InjectMocks
    private S3ImageService s3ImageService;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private MultipartFile image;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(s3ImageService, "bucketName", "your-bucket-name");

    }

    @Test
    void testUpload_Success() throws Exception {
        // Given
        String originalFilename = "test.jpg";
        String s3FileName = "test-s3-file-name"; // mock에서 사용할 S3 파일 이름
        String expectedUrl = "http://your-url.com/test.jpg";

        when(image.getOriginalFilename()).thenReturn(originalFilename);
        when(image.getInputStream()).thenReturn(new ByteArrayInputStream("test data".getBytes()));
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(4L); // 파일 크기 설정

        // amazonS3.putObject() 호출에 대한 모의 설정
        PutObjectResult putObjectResult = new PutObjectResult();
        when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(putObjectResult);

        // amazonS3.getUrl() 호출에 대한 모의 설정
        when(amazonS3.getUrl("your-bucket-name", s3FileName)).thenReturn(new URL(expectedUrl));

        // When
        String result = s3ImageService.upload(image);

        // Then
        assertNotNull(result);
        assertEquals(expectedUrl, result); // 결과가 예상 URL과 일치하는지 확인
    }


    @Test
    void testValidateImage_EmptyFile() {
        // Given
        when(image.isEmpty()).thenReturn(true);

        // When & Then
        assertThrows(CustomImageException.class, () -> s3ImageService.upload(image));
    }

    @Test
    void testDeleteImageFromS3_Success() {
        // Given
        String imageAddress = "http://example.com/test.jpg";
        doNothing().when(amazonS3).deleteObject(any(DeleteObjectRequest.class));

        // When
        s3ImageService.deleteImageFromS3(imageAddress);

        // Then
        verify(amazonS3).deleteObject(any(DeleteObjectRequest.class)); // S3에서 삭제가 호출되었는지 확인
    }

    @Test
    void testDeleteImageFromS3_Failure() {
        // Given
        String imageAddress = "http://example.com/test.jpg";
        doThrow(new AmazonServiceException("Error")).when(amazonS3).deleteObject(any(DeleteObjectRequest.class));

        // When & Then
        assertThrows(CustomImageException.class, () -> s3ImageService.deleteImageFromS3(imageAddress));
    }
}
