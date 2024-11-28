package com.dorandoran.backend.member.Controller;

import com.dorandoran.backend.member.domain.MemberService;
import com.dorandoran.backend.member.domain.SimpleUserDetails;
import com.dorandoran.backend.member.dto.req.LoginRequest;
import com.dorandoran.backend.member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.member.dto.req.SignUpDTO;
import com.dorandoran.backend.member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.common.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입
     */
    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "중복된 아이디 또는 이메일", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        Long memberId = memberService.signUp(signUpDTO);
        return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 요청을 통해 인증된 사용자의 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MemberResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest httpRequest) {
        MemberResponseDTO responseDTO = memberService.login(loginRequest, httpRequest);
        return ResponseEntity.ok(responseDTO);
        }

    /**
     * 회원 정보 조회
     */
    @Operation(summary = "회원 정보 조회", description = "현재 로그인한 회원의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/findMember")
    public ResponseEntity<MemberResponseDTO> getMemberInfo(@AuthenticationPrincipal SimpleUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        MemberResponseDTO memberInfo = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    /**
     * 회원 정보 업데이트
     */
    @Operation(summary = "회원 정보 업데이트", description = "회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/updateMember")
    public ResponseEntity<MemberResponseDTO> updateMemberInfo(@AuthenticationPrincipal SimpleUserDetails userDetails, @RequestBody MemberUpdateRequestDTO updateRequestDTO) {
        Long memberId = userDetails.getMemberId();
        MemberResponseDTO updatedMember = memberService.updateMemberInfo(memberId, updateRequestDTO);
        return ResponseEntity.ok(updatedMember);
    }

    /**
     * 회원 정보 삭제
     */
    @Operation(summary = "회원 삭제", description = "회원 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/deleteMember")
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal SimpleUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }


}
