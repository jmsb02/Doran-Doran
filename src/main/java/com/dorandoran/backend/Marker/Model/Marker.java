package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class Marker extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marker_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "marker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @Builder
    public Marker(Member member, String title, String address, String content, Double latitude, Double longitude) {
        this.member = member;
        this.title = title;
        this.address = address;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addFile(File file) {
        if (!files.contains(file)) {
            files.add(file);
            file.assignMarker(this); // 파일의 마커를 현재 마커로 설정
        }
    }

    public void removeFile(File file) {
        if (files.remove(file)) {
            file.assignMarker(null); // 파일의 마커 참조 해제
        }
    }
    //createdAt 엔티티 클래스는 BaseEntity를 상속받아 공통 필드를 재사용 하도록 수정.
}