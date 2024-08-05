package com.dorandoran.backend.File.Model;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public void setPost(Post post) {
        this.post = post;
        if (post != null && !post.getFiles().contains(this)) {
            post.addFile(this); //게시물에 파일 추가
        }
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


    public File(String originalFilename) {
        this.originalFilename = originalFilename;
        this.storeFilename = getFileName(originalFilename);
        this.accessUrl = "";
    }

    // 이미지 파일의 확장자를 추출하는 메소드
    public String extractExtension(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        return originalFilename.substring(index, originalFilename.length());
    }

    // 이미지 파일의 이름을 저장하기 위한 이름으로 변환하는 메소드
    public String getFileName(String originalFilename) {
        return UUID.randomUUID() + "." + extractExtension(originalFilename);
    }

}

