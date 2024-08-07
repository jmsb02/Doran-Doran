package com.dorandoran.backend.Post.Model;

import com.dorandoran.backend.Comment.Model.Comment;
import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.Member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime update_at;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    //연관관계 편의 메서드
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void addFile(File file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
        file.setPost(this); //파일의 게시글 참조 설정
    }

    @Builder
    private Post(String title, String content, LocalDateTime created_at, Member member) {
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.member = member;
    }

    // Post 클래스
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.update_at = LocalDateTime.now(); // 수정일자 업데이트

    }
}