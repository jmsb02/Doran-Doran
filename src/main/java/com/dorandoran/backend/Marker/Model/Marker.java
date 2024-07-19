package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.Club.Model.Club;
import com.dorandoran.backend.Member.Model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marker_id")
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany
    @JoinColumn(name = "marker_id")
    private List<Club> clubs = new ArrayList<>();

}