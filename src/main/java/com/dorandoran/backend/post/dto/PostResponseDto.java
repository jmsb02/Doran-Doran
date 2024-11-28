package com.dorandoran.backend.post.dto;

import com.dorandoran.backend.file.domain.File;

import com.dorandoran.backend.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "마커 관련 포스팅 정보 응답 반환 데이터")
public class PostResponseDto {

    @Schema(description = "포스팅 작성자", example = "더벤티")
    private String memberName;

    @Schema(description = "포스팅 제목", example = "더벤티")
    private String title;

    @Schema(description = "포스팅 내용", example = "더벤티 논현역점")
    private String content;

//    @Schema(description = "마커 주소")
//    private MarkerAddress address; // 또는 Address 객체

    @Schema(description = "포스팅 파일")
    private List<File> files; // 파일 정보 DTO

    @Schema(description = "포스팅 생성 일자")
    private LocalDateTime createdAt; // 생성일자

    public PostResponseDto(String memberName, String title, String content, List<File> files, LocalDateTime createdAt) {
        this.memberName = memberName;
        this.title = title;
        this.content = content;
        this.files = files;
        this.createdAt = createdAt;
    }

//    public PostResponseDto(Post post) {
//        this.memberName = post.getUserName();
//        this.title = post.getTitle();
//        this.content = post.getContent();
//        this.files = post.getMarker().getFiles();
//        this.createdAt = post.getCreatedAt();
//    }

}
