package com.dorandoran.backend.marker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    List<Marker> findByMemberId(Long memberId);
}