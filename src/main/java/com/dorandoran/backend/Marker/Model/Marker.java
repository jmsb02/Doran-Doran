package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.Member.domain.Address;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "marker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    public void setFiles(List<File> files) {
        this.files = files;
        for (File file : files) {
            file.setMarker(this);
        }
    }

    @Builder
    public Marker(Long id, Member member, String title, String content, Address address, List<File> files) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.address = address;
        this.files = files != null ? files : new ArrayList<>(); // null 체크 후 빈 리스트 할당
    }

}
