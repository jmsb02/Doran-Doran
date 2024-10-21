package com.dorandoran.backend.File.Model;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.dorandoran.backend.File.exception.CustomImageException;
import com.dorandoran.backend.File.exception.FileMissingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    private File file;

    @BeforeEach
    void setUp() {
        file = new File("originalName.jpg", "generateName", 1024L, "image/jpeg", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAAAAAAAD");
    }

    /**
     * 파일 생성 테스트
     */
    @Test
    void createFile_Test() {
        // Given
        when(fileRepository.save(any(File.class))).thenReturn(file);

        // When
        File resultFile = fileService.createFile("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAAAAAAAD", "originalFileName.jpg");

        // Then
        assertNotNull(resultFile);
        assertEquals("originalName.jpg", resultFile.getOriginalFilename());
        verify(fileRepository, times(1)).save(any(File.class));
    }


    /**
     * 파일 유무 테스트 (성공)
     */
    @Test
    void getFileById_FileExists() {

        // Given
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        // When
        File foundFile = fileService.getFileById(fileId);

        // Then
        assertNotNull(foundFile);
        assertEquals(file.getOriginalFilename(), foundFile.getOriginalFilename());
        verify(fileRepository, times(1)).findById(fileId);
    }

    /**
     * 파일 유무 테스트 (실패)
     */
    @Test
    void getFileById_FileNotFound() {

        // Given
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(FileMissingException.class, () -> fileService.getFileById(fileId));
    }

    @Test
    void updateFile() throws IOException {

        //Given
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        when(fileRepository.save(any(File.class))).thenReturn(file);

        //Mocking MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn("data".getBytes());
        when(mockFile.getContentType()).thenReturn("image/jpeg");

        //When
        File updateFile = fileService.updateFile(1L, "newName.jpg", mockFile);

        //Then
        assertNotNull(updateFile);
        assertEquals("newName.jpg", updateFile.getStoreFilename());
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    void deleteFile() {

        //Given
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        //When
        fileService.deleteFile(1L);

        //Then
        verify(fileRepository, times(1)).delete(file);
    }


}
