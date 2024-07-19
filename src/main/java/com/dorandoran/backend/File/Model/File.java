package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.Post.Model.Post;
import com.dorandoran.backend.Member.Model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
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

}

