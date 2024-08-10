package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.Marker.Model.Marker;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Post.Model.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String originalFilename; //이미지 파일 본래 이름

    @Column(nullable = false)
    private String storeFilename; //이미지 파일이 S3에 저장될 때 사용되는 이름

    @Column
    private String accessUrl; //S3 내부 이미지에 접근할 수 있는 URL

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String filePath; // 파일 저장 경로

    private String s3Url; // S3 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marker_id", nullable = false)
    private Marker marker;

    private String fileName;

    // 기본 생성자
    public File() {}

    // 파일 이름을 매개변수로 받는 생성자
    public File(String fileName) {
        this.fileName = fileName;
    }


    // 파일 이름과 원본 파일 이름을 매개변수로 받는 생성자
    public File(String originalFilename, String fileName) {
        this.originalFilename = originalFilename;
        this.fileName = fileName;
        this.storeFilename = "";
        this.accessUrl = "";
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // setStoreFilename 메서드 추가
    public void setStoreFilename(String storeFilename) {
        this.storeFilename = storeFilename;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url; // S3 URL 설정
    }

    // fileName을 설정하는 메서드
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    // 연관관계 메서드 로직을 위한 해당 필드 값 할당 및 캡슐화 유지
    public void assignPost(Post post) {
        this.post = post;
    }

    public void assignMarker(Marker marker) {
        this.marker = marker;
    }




}

