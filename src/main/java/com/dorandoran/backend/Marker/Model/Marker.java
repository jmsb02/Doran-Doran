package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.common.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Member author;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    /**
     * 파일 추가
     */


    public Marker(Member author, String name, Double latitude, Double longitude) {
        this.author = author;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //createdAt 엔티티 클래스는 BaseEntity를 상속받아 공통 필드를 재사용 하도록 수정.
}