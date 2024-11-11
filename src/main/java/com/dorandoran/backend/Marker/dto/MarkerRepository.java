package com.dorandoran.backend.Marker.dto;

import com.dorandoran.backend.Marker.domain.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    List<Marker> findByMemberId(Long memberId);
}