package com.dorandoran.backend.File.Model;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.dorandoran.backend.File.exception.CustomS3Exception;
import com.dorandoran.backend.File.exception.FileMissingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private S3ImageService s3ImageService;

    @Mock
    private MultipartFile image;

    @Mock
    private AmazonS3 amazonS3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createFile_Success() {
        // Given
        String originalFilename = "test.jpg";
        String imageUrl = "http://your-url.com/test.jpg";
        when(image.getOriginalFilename()).thenReturn(originalFilename);
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(4L);
        when(image.getContentType()).thenReturn("image/jpeg");
        when(s3ImageService.upload(image)).thenReturn(imageUrl);
        when(fileRepository.save(any(File.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        File file = fileService.createFile(image);

        // Then
        assertNotNull(file);
        assertEquals(originalFilename, file.getFileName());
        assertEquals(imageUrl, file.getAccessUrl());
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    void getFileById_FileExists() {
        // Given
        Long fileId = 1L;
        File file = new File();
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        // When
        File foundFile = fileService.getFileById(fileId);

        // Then
        assertNotNull(foundFile);
        verify(fileRepository, times(1)).findById(fileId);
    }

    @Test
    void getFileById_FileNotFound() {
        // Given
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(FileMissingException.class, () -> fileService.getFileById(fileId));
    }

    @Test
    void updateFile_Success() {
        // Given
        Long fileId = 1L;
        String newFileName = "newTest.jpg";
        File existingFile = new File();
        existingFile.setAccessUrl("http://your-url.com/old.jpg");
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(existingFile));
        when(image.getOriginalFilename()).thenReturn(newFileName);
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(4L);
        when(image.getContentType()).thenReturn("image/jpeg");
        when(s3ImageService.upload(image)).thenReturn("http://your-url.com/new.jpg");
        when(fileRepository.save(any(File.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        File updatedFile = fileService.updateFile(fileId, newFileName, image);

        // Then
        assertNotNull(updatedFile); // Null 체크 추가
        assertEquals(newFileName, updatedFile.getFileName());
        assertEquals("http://your-url.com/new.jpg", updatedFile.getAccessUrl());
        verify(s3ImageService, times(1)).deleteImageFromS3(existingFile.getAccessUrl());
        verify(fileRepository, times(1)).save(updatedFile);
    }


    @Test
    void deleteFile_Success() {
        // Given
        Long fileId = 1L;
        File file = new File();
        file.setAccessUrl("http://your-url.com/new.jpg"); // 올바른 URL로 설정
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        // S3에서 삭제 메서드가 호출될 때의 Mock 설정
        doNothing().when(s3ImageService).deleteImageFromS3(file.getAccessUrl());
        // When
        fileService.deleteFile(fileId);


        // Then
        // S3에서 이미지 삭제 메서드가 호출되었는지 확인
        verify(s3ImageService, times(1)).deleteImageFromS3(file.getAccessUrl());
        // AmazonS3의 deleteObject 메서드가 호출되었는지 확인
        verify(amazonS3, times(1)).deleteObject(any(DeleteObjectRequest.class));
        // 파일 리포지토리에서 삭제 메서드가 호출되었는지 확인
        verify(fileRepository, times(1)).delete(file);
    }


    @Test
    void validateImage_EmptyFile() {
        // Given
        when(image.isEmpty()).thenReturn(true);

        // When & Then
        assertThrows(CustomS3Exception.class, () -> fileService.validateImage(image));
    }

    @Test
    void validateImage_FileTooLarge() {
        // Given
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(6L * 1024 * 1024); // 6MB
        when(image.getContentType()).thenReturn("image/jpeg");

        // When & Then
        assertThrows(CustomS3Exception.class, () -> fileService.validateImage(image));
    }

    @Test
    void validateImage_InvalidFileType() {
        // Given
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(4L);
        when(image.getContentType()).thenReturn("application/pdf"); // Invalid type

        // When & Then
        assertThrows(CustomS3Exception.class, () -> fileService.validateImage(image));
    }
}
