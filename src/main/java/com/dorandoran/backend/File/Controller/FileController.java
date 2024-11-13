package com.dorandoran.backend.File.Controller;

import com.dorandoran.backend.File.DTO.FileDTO;
import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileService;
import com.dorandoran.backend.common.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Upload a new file", description = "새 파일을 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "파일 업로드됨", content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data") // <-- 여기서 consumes 설정 추가
    public ResponseEntity<FileDTO> uploadFile(
            @Parameter(description = "업로드할 파일", required = true)
            @RequestParam("file") MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String base64Image = fileService.convertToBase64(file);
        fileService.validateImage(base64Image);
        Long fileId = fileService.createFile(base64Image, originalFileName);
        FileDTO fileDTO = fileService.findFileOne(fileId);

        return new ResponseEntity<>(fileDTO, HttpStatus.CREATED);
    }

    // 파일 수정
    @Operation(summary = "Update an existing file", description = "기존 파일을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업데이트 됨", content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileDTO.class))),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/update/{fileId}", consumes = "multipart/form-data")
    public ResponseEntity<File> updateFile(
            @Parameter(description = "수정된 파일 ID") @PathVariable("fileId") Long fileId,
            @Parameter(description = "새 파일", required = true, content = @Content(mediaType = "application/octet-stream"))
            @RequestParam("file") MultipartFile file) {

        // 파일 이름을 가져옵니다.
        String newFileName = file.getOriginalFilename();

        // 파일을 업데이트하고 결과를 가져옵니다.
        File updatedFile = fileService.updateFile(fileId, newFileName, file);

        // 응답 반환
        return ResponseEntity.ok(updatedFile);
    }

    //파일 조회
    @Operation(summary = "Get file by ID", description = "파일 ID로 파일 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 조회됨", content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileDTO.class))),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{fileId}")
    public ResponseEntity<File> getFile(
            @Parameter(description = "조회할 파일 ID") @PathVariable("fileId") Long id) {
        File findFile = fileService.getFileById(id);
        return ResponseEntity.ok(findFile);
    }

    // 파일 삭제
    @Operation(summary = "Delete a file by ID", description = "파일 ID로 파일을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "파일 삭제됨"),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @Parameter(description = "삭제할 파일 ID") @PathVariable("fileId") Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

}
