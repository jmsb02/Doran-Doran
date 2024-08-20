package com.dorandoran.backend.Marker.Model;

import com.dorandoran.backend.Marker.dto.MarkerCheckDTO;
import com.dorandoran.backend.Marker.dto.MarkerDTO;
import com.dorandoran.backend.Marker.dto.MarkerFindDTO;
import com.dorandoran.backend.Member.domain.Address;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MarkerCommandServiceTest {

    @InjectMocks
    private MarkerCommandService markerCommandService;

    @Mock
    private MarkerRepository markerRepository;

    @Mock
    private MemberRepository memberRepository;



    private Member testMember;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Address 객체 생성
        Address address = new Address("12345", "Main St", "Apt 101");

        // Member 객체 생성 (클래스 필드에 직접 할당)
        testMember = Member.builder() // 'testMember'로 직접 초기화
                .id(1L)
                .name("name")
                .email("email@naver.com")
                .loginId("loginId")
                .password("password1!")
                .address(address)
                .build();

    }

    @Test
    void saveMarker() {
        //given
        MarkerDTO markerDTO = new MarkerDTO(1L, "Test Address", "Test title", "Test Content", 11.0, 22.0, Collections.emptyList());
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        // 빌더 패턴을 사용하여 Marker 객체 생성
        Marker mockMarker = Marker.builder()
                .id(1L)
                .member(testMember)
                .title("Test title")
                .address("Test Address")
                .content("Test Content")
                .latitude(11.0)
                .longitude(22.0)
                .build();

        // when
        when(markerRepository.save(any(Marker.class))).thenReturn(mockMarker);

        // 실제 saveMarker 메소드를 호출
        Long markerId = markerCommandService.saveMarker(markerDTO, testMember);

        //then
        assertThat(markerId).isEqualTo(1L);
    }

    @Test
    void findMarkerOne() {

        //given
        Long markerId = 1L;
        Marker mockMarker = Marker.builder()
                .id(markerId)
                .member(testMember)
                .address("Test Address")
                .title("Test Title")
                .content("Test Content")
                .latitude(11.0)
                .longitude(22.0)
                .build();

        when(markerRepository.findById(mockMarker.getId())).thenReturn(Optional.of(mockMarker));

        //when
        //mockMarker를 기반으로 MarkerCheckDTO 생성
        MarkerCheckDTO result = markerCommandService.findMarkerOne(markerId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(markerId);
        assertThat(result.getAddress()).isEqualTo("Test Address");
        assertThat(result.getTitle()).isEqualTo("Test Title");
    }


    @Test
    void findMarkersUsers() {
        // given
        Long memberId = 1L;
        Marker mockMarker1 = Marker.builder()
                .id(1L)
                .member(testMember)
                .address("Address 1")
                .title("Title 1")
                .content("Content 1")
                .latitude(11.0)
                .longitude(22.0)
                .files(new ArrayList<>()) // 빈 리스트 초기화
                .build();

        Marker mockMarker2 = Marker.builder()
                .id(2L)
                .member(new Member(2L, "Test name", "email@gmail.com", "loginId2", "password2!", new Address()))
                .address("Address 2")
                .title("Title 2")
                .content("Content 2")
                .latitude(11.0)
                .longitude(22.0)
                .files(new ArrayList<>()) // 빈 리스트 초기화
                .build();

        when(markerRepository.findByMemberId(memberId)).thenReturn(List.of(mockMarker1, mockMarker2));

        // when
        List<MarkerCheckDTO> markersUsers = markerCommandService.findMarkersUsers(memberId);// 메소드 이름 수정

        // then
        assertThat(markersUsers).hasSize(2);
        assertThat(markersUsers.get(0).getId()).isEqualTo(1L);
        assertThat(markersUsers.get(1).getId()).isEqualTo(2L);
    }


    @Test
    void findAllMarkers() {

        // given
        Marker mockMarker1 = Marker.builder()
                .id(1L)
                .member(testMember)
                .address("Address 1")
                .title("Title 1")
                .content("Content 1")
                .latitude(11.0)
                .longitude(22.0)
                .build();

        Marker mockMarker2 = Marker.builder()
                .id(2L)
                .member(new Member(2L, "Test name", "email@gmail.com", "loginId2", "password2!", new Address()))
                .address("Address 2")
                .title("Title 2")
                .content("Content 2")
                .latitude(11.0)
                .longitude(22.0)
                .build();

        when(markerRepository.findAll()).thenReturn(List.of(mockMarker1, mockMarker2));
        //when
        List<MarkerFindDTO> allMarkers = markerCommandService.findAllMarkers();

        // then
        assertThat(allMarkers).hasSize(2);
        assertThat(allMarkers.get(0).getId()).isEqualTo(1L);
        assertThat(allMarkers.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void deleteMarker() {
        //given
        Long markerId = 1L;
        Marker mockMarker = Marker.builder()
                .id(markerId)
                .member(testMember)
                .address("Test Address")
                .title("Test Title")
                .content("Test Content")
                .latitude(11.0)
                .longitude(22.0)
                .build();

        when(markerRepository.findById(markerId)).thenReturn(Optional.of(mockMarker));

        //when
        markerCommandService.deleteMarker(markerId);

        //then
        verify(markerRepository).delete(mockMarker);

    }
}
