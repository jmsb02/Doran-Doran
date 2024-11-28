package com.dorandoran.backend.marker.Model;


import com.dorandoran.backend.Marker.domain.MarkerService;

import com.dorandoran.backend.file.domain.FileService;

import com.dorandoran.backend.marker.domain.Marker;
import com.dorandoran.backend.marker.domain.MarkerAddress;
import com.dorandoran.backend.marker.domain.MarkerRepository;
import com.dorandoran.backend.marker.dto.MarkerDTO;
import com.dorandoran.backend.marker.dto.MarkerResponseDto;
import com.dorandoran.backend.member.domain.Member;
import com.dorandoran.backend.member.domain.MemberAddress;
import com.dorandoran.backend.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MarkerServiceTest {

    @InjectMocks
    private MarkerService markerService;

    @Mock
    private MarkerRepository markerRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FileService fileService;

    private Member testMember;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Address 객체 생성
        MemberAddress address = new MemberAddress(1.0, 2.0);

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
        // Given
        MarkerDTO markerDTO = new MarkerDTO("title", "content", new MarkerAddress(1.0, 2.0));
        List<MultipartFile> imageFiles = new ArrayList<>();
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        // Mock 마커 객체 생성 및 저장 후 반환할 마커 객체 설정
        Marker mockMarker = Marker.builder()
                .id(1L) // 설정한 마커 ID
                .member(testMember)
                .title("Test title")
                .address(markerDTO.getAddress())
                .content("Test Content")
                .files(new ArrayList<>())
                .build();

        // `markerRepository.save`가 `mockMarker`를 반환하도록 설정
        when(markerRepository.save(any(Marker.class))).thenReturn(mockMarker);

        // When
        Long markerId = markerService.saveMarker(markerDTO, imageFiles, testMember);

        // Then
        assertThat(markerId).isNotNull(); // `markerId`가 null이 아님을 확인
        assertThat(markerId).isEqualTo(1L); // 기대하는 ID가 맞는지 확인
    }


    @Test
    void findMarkerOne() {

        //given
        Long markerId = 1L;
        MarkerDTO markerDTO = new MarkerDTO("title", "content", new MarkerAddress(1.0, 2.0));

        Marker mockMarker = Marker.builder()
                .id(markerId)
                .member(testMember)
                .address(markerDTO.getAddress())
                .title("Test Title")
                .content("Test Content")
                .files(new ArrayList<>())
                .build();

        when(markerRepository.findById(mockMarker.getId())).thenReturn(Optional.of(mockMarker));

        //when
        //mockMarker를 기반으로 MarkerCheckDTO 생성
        MarkerResponseDto result = markerService.findMarkerOne(markerId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getAddress()).isEqualTo(testMember.getAddress());
        assertThat(result.getTitle()).isEqualTo("Test Title");
    }


    @Test
    void findMarkersUsers() {
        // given
        Long memberId = 1L;
        MarkerDTO markerDTO = new MarkerDTO("title", "content", new MarkerAddress(1.0, 2.0));

        Marker mockMarker1 = Marker.builder()
                .id(1L)
                .member(testMember)
                .address(markerDTO.getAddress())
                .title("Title 1")
                .content("Content 1")
                .files(new ArrayList<>()) // 빈 리스트 초기화
                .build();

        Marker mockMarker2 = Marker.builder()
                .id(2L)
                .member(testMember)
                .address(markerDTO.getAddress())
                .title("Title 2")
                .content("Content 2")
                .files(new ArrayList<>()) // 빈 리스트 초기화
                .build();

        when(markerRepository.findByMemberId(memberId)).thenReturn(List.of(mockMarker1, mockMarker2));

        // when
        List<MarkerResponseDto> markersUsers = markerService.findMarkersUsers(memberId);// 메소드 이름 수정

        // then
        assertThat(markersUsers).hasSize(2);
        assertThat(markersUsers.get(0).getTitle()).isEqualTo("Title 1");
        assertThat(markersUsers.get(1).getTitle()).isEqualTo("Title 2");
    }


    @Test
    void findAllMarkers() {

        // given

        MarkerDTO markerDTO = new MarkerDTO("title", "content", new MarkerAddress(1.0, 2.0));


        Marker mockMarker1 = Marker.builder()
                .id(1L)
                .member(testMember)
                .address(markerDTO.getAddress())
                .title("Title 1")
                .content("Content 1")
                .files(new ArrayList<>())
                .build();


        Member testMember2 = new Member(3L, "name2", "2@naver.com", "loginId1", "password!@2", new MemberAddress(2.0, 3.0), "image2.png");

        Marker mockMarker2 = Marker.builder()
                .id(2L)
                .member(testMember2)
                .address(markerDTO.getAddress())
                .title("Title 2")
                .content("Content 2")
                .files(new ArrayList<>())
                .build();

        when(markerRepository.findAll()).thenReturn(List.of(mockMarker1, mockMarker2));
        //when
        List<MarkerResponseDto> allMarkers = markerService.findAllMarkers();

        // then
        assertThat(allMarkers).hasSize(2);
        assertThat(allMarkers.get(0).getTitle()).isEqualTo("Title 1");
        assertThat(allMarkers.get(1).getTitle()).isEqualTo("Title 2");
    }

    @Test
    void deleteMarker() {
        //given

        MarkerDTO markerDTO = new MarkerDTO("title", "content", new MarkerAddress(1.0, 2.0));


        Long markerId = 1L;
        Marker mockMarker = Marker.builder()
                .id(markerId)
                .member(testMember)
                .address(markerDTO.getAddress())
                .title("Test Title")
                .content("Test Content")
                .files(new ArrayList<>())
                .build();

        when(markerRepository.findById(markerId)).thenReturn(Optional.of(mockMarker));

        //when
        markerService.deleteMarker(markerId);

        //then
        verify(markerRepository).delete(mockMarker);

    }
}
