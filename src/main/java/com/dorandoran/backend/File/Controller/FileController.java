package com.dorandoran.backend.File.Controller;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import com.dorandoran.backend.File.Model.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final S3ImageService s3ImageService;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam(value = "image", required = false) MultipartFile image) {
        fileService.validateImage(image);
        File file = fileService.createFile(image);
        return ResponseEntity.ok(file);
    }

    // 파일 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<File> updateFile(@PathVariable("id") Long id, @RequestParam("file") MultipartFile newFile) {
        // 파일 이름을 가져옵니다.
        String newFileName = newFile.getOriginalFilename();

        // 파일을 업데이트하고 결과를 가져옵니다.
        File updatedFile = fileService.updateFile(id, newFileName, newFile);

        // 응답 반환
        return ResponseEntity.ok(updatedFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFile(@PathVariable("id") Long id) {
        File findFile = fileService.getFileById(id);
        return ResponseEntity.ok(findFile);
    }

    // 파일 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    //s3 이미지 삭제
    @GetMapping("/s3/delete")
    public ResponseEntity<Void> s3Delete(@RequestParam String addr) {
        s3ImageService.deleteImageFromS3(addr);
        return ResponseEntity.noContent().build();
    }
}
