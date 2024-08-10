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
        File file = fileService.createFile(image.getOriginalFilename());
        String s3Url = s3ImageService.upload(image);
        file.setS3Url(s3Url);
        return ResponseEntity.ok(file);
    }

    //파일 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<File> updateFile(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        fileService.validateImage(file);
        File findFile = fileService.getFileById(id);
        String url = s3ImageService.upload(file);
        findFile.setS3Url(url);

        String newFileName = file.getOriginalFilename();
        fileService.updateFile(id, newFileName);

        return ResponseEntity.ok(findFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFile(@PathVariable("id") Long id) {
        File findFile = fileService.getFileById(id);
        return ResponseEntity.ok(findFile);
    }

    // 파일 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") Long id) {
        File file = fileService.getFileById(id);
        s3ImageService.deleteImageFromS3(file.getS3Url());
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
