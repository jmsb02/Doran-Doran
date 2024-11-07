package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.Marker.Model.Marker;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Post.Model.Post;
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

//    @Column(nullable = false)
//    private String filePath; // 파일 저장 경로

    //Base64 데이터 저장
    @Column(columnDefinition = "TEXT") //큰 데이터 저장 위해 TEXT 타입 사용
    private String base64Data; //Base64로 인코딩 된 이미지 데이터

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marker_id", nullable = false)
    private Marker marker;

    public void assignMarker(Marker marker) {
        this.marker = marker;
        if (marker != null && !marker.getFiles().contains(this)) {
            marker.addFile(this); // 마커에 파일 추가
        }
    }


    // 파일 이름과 원본 파일 이름을 매개변수로 받는 생성자
    public File(String originalFilename, String storeFilename, Long fileSize, String fileType, String base64Data) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.base64Data = base64Data; // Base64 데이터 포함
    }
}

