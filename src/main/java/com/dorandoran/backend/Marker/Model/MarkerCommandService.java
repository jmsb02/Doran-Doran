package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Marker.exception.MarkerNotFoundException;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MarkerCommandService {

    private final MarkerRepository markerRepository;
    private final MemberRepository memberRepository;

    /**
     * 새로운 마커 저장
     */
    public Long saveMarker(MarkerDTO markerDTO) {

        //Member 조회
        Member findMember = memberRepository.findById(markerDTO.getMember_id()).orElseThrow(() -> new MemberNotFoundException());

        //마커 생성 및 저장
        Marker marker = new Marker(findMember, markerDTO.getName(), markerDTO.getLatitude(), markerDTO.getLongitude());
        Marker savedMarker = markerRepository.save(marker);

        //저장된 마커의 ID 반환
        return savedMarker.getId();
    }
}
