package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.Member.domain.Address;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "marker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();


    @Builder
    public Marker(Long id, Member member, String title, String content, Address address, List<File> files) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.address = address;
        this.files = files != null ? files : new ArrayList<>(); // null 체크 후 빈 리스트 할당
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

}
