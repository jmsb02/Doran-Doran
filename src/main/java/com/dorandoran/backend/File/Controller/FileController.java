package com.dorandoran.backend.File.Controller;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam(value = "base64Image", required = false) String base64Image,
                                           @RequestParam(value = "originalFileName", required = false) String originalFileName) {
        fileService.validateImage(base64Image);
        File file = fileService.createFile(base64Image, originalFileName);
        return ResponseEntity.ok(file);
    }

    // 파일 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<File> updateFile(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {

        // 파일 이름을 가져옵니다.
        String newFileName = file.getOriginalFilename();

        // 파일을 업데이트하고 결과를 가져옵니다.
        File updatedFile = fileService.updateFile(id, newFileName, file);

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

}
