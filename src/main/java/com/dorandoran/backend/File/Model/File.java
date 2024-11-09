package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.Marker.Model.Marker;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Post.Model.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String originalFilename; //이미지 파일 본래 이름

    @Column(nullable = false)
    private String storeFilename; //이미지 파일 이름 (UUID 등으로 저장)

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String fileType;

    //Base64 데이터 저장
    @Column(columnDefinition = "TEXT") //큰 데이터 저장 위해 TEXT 타입 사용
    private String base64Data; //Base64로 인코딩 된 이미지 데이터

    //필요한 경우에만 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    //필요한 경우에만 연결
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "marker_id", nullable = true)
    private Marker marker;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "post_id", nullable = false)
    //private Post post;

    // 파일 이름과 원본 파일 이름을 매개변수로 받는 생성자
    public File(String originalFilename, String storeFilename, Long fileSize, String fileType, String base64Data) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.base64Data = base64Data; // Base64 데이터 포함
    }
}

