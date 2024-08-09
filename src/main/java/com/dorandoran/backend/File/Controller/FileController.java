package com.dorandoran.backend.File.Controller;

import com.dorandoran.backend.File.Model.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final S3ImageService s3ImageService;

    @PostMapping("/s3/upload")
    public ResponseEntity<String> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image) {
        String profileImage = s3ImageService.upload(image);
        return ResponseEntity.ok(profileImage);
    }

    @GetMapping("/s3/delete")
    public ResponseEntity<Void> s3delete(@RequestParam String addr) {
        s3ImageService.deleteImageFromS3(addr);
        return ResponseEntity.ok(null);
    }
}
