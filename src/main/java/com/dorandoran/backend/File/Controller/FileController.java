package com.dorandoran.backend.File.Controller;

import com.dorandoran.backend.File.DTO.FileDTO;
import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
@Slf4j
public class FileController {

    private final FileService fileService;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file) {

        String originalFileName = file.getOriginalFilename();

        String base64Image = fileService.convertToBase64(file);

        fileService.validateImage(base64Image);

        Long fileId = fileService.createFile(base64Image, originalFileName);

        FileDTO fileDTO = fileService.findFileOne(fileId);

        return new ResponseEntity<>(fileDTO, HttpStatus.CREATED);
    }

    // 파일 수정
    @PutMapping("/update/{fileId}")
    public ResponseEntity<File> updateFile(@PathVariable("fileId") Long fileId, @RequestParam("file") MultipartFile file) {

        // 파일 이름을 가져옵니다.
        String newFileName = file.getOriginalFilename();

        // 파일을 업데이트하고 결과를 가져옵니다.
        File updatedFile = fileService.updateFile(fileId, newFileName, file);

        // 응답 반환
        return ResponseEntity.ok(updatedFile);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<File> getFile(@PathVariable("fileId") Long id) {
        File findFile = fileService.getFileById(id);
        return ResponseEntity.ok(findFile);
    }

    // 파일 삭제
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable("fileId") Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

}
