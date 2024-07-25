package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.Member.Model.Member;
import com.dorandoran.backend.Post.Model.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String storeFilename;

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
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;


    public void setPost(Post post) {
        this.post = post;
        if(post != null && !post.getFiles().contains(this)) {
            post.addFile(this); //게시물에 파일 추가
        }
        }

}

