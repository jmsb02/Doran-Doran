package com.dorandoran.backend.Marker.Controller;

import com.dorandoran.backend.Marker.domain.MarkerService;
import com.dorandoran.backend.Marker.dto.*;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.SimpleUserDetails;
import com.dorandoran.backend.common.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/markers")
@Slf4j
public class MarkerController {

    private final MarkerService markerService;

    //마커 작성
    @Operation(summary = "Create a new marker", description = "새로운 마커를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "마커 생성됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarkerFindDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MarkerFindDTO> createMarker(@RequestPart("markerDTO") @Valid @Schema(implementation = MarkerDTO.class, description = "마커 생성 요청 데이터") MarkerDTO markerDTO,
                                                      @RequestPart("files") @Parameter(description = "업로드할 파일 목록") List<MultipartFile> files,
                                                      @Parameter(hidden = true) @AuthenticationPrincipal SimpleUserDetails userDetails) {

        Member findMember = userDetails.getMember();
        Long markerId = markerService.saveMarker(markerDTO, files, findMember);
        MarkerFindDTO markerFindDTO = markerService.findMarkerOne(markerId);
        return new ResponseEntity<>(markerFindDTO, HttpStatus.CREATED);
    }

    //마커 단일 조회
    @Operation(summary = "Get marker by ID", description = "특정 마커를 ID로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마커 조회됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarkerFindDTO.class))),
            @ApiResponse(responseCode = "404", description = "마커를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{markerId}")
    public ResponseEntity<MarkerFindDTO> findPostOne(
            @Parameter(description = "조회할 마커 ID") @PathVariable("markerId") Long markerId) {
        MarkerFindDTO marker = markerService.findMarkerOne(markerId);
        return ResponseEntity.ok(marker);
    }

    //특정 사용자 마커 조회
    @Operation(summary = "Get markers by user ID", description = "특정 사용자가 작성한 마커들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 마커 목록", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MarkerFindDTO.class)))),
            @ApiResponse(responseCode = "404", description = "사용자가 작성한 마커를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MarkerFindDTO>> findMarkersByUser(
            @Parameter(description = "조회할 마커 ID") @PathVariable("memberId") Long memberId) {
        List<MarkerFindDTO> markers = markerService.findMarkersUsers(memberId);
        return ResponseEntity.ok(markers);
    }

    //전체 마커 조회
    @Operation(summary = "Get all markers", description = "모든 마커를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 마커 목록",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MarkerFindDTO.class))))
    @GetMapping("/allMarkers")
    public ResponseEntity<List<MarkerFindDTO>> findAllMarkers() {
        List<MarkerFindDTO> markers = markerService.findAllMarkers();
        return ResponseEntity.ok(markers);
    }

    //마커 삭제
    @Operation(summary = "Delete marker", description = "특정 마커를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "마커 삭제됨"),
            @ApiResponse(responseCode = "404", description = "마커를 찾을 수 없음",content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{markerId}")
    public ResponseEntity<Void> deleteMarker(@PathVariable("markerId") Long markerId) {
        markerService.deleteMarker(markerId);
        return ResponseEntity.noContent().build();
    }
}
