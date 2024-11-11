//package com.dorandoran.backend.Member.domain;
//
//import com.dorandoran.backend.Member.dto.req.LoginRequest;
//import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
//import com.dorandoran.backend.Member.dto.req.SignUpDTO;
//import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
//import com.dorandoran.backend.Member.exception.DuplicateMemberException;
//import com.dorandoran.backend.Member.exception.MemberNotFoundException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//class MemberServiceTest {
//
//    @InjectMocks
//    private MemberService memberService;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    private Member testMember;
//
//    @BeforeEach
//    public void setUp() {
//
//        MockitoAnnotations.openMocks(this);
//
//        testMember = Member.builder()
//                .id(1L)
//                .name("Test User")
//                .email("test@example.com")
//                .loginId("testUser")
//                .password("password1!")
//                .address(new MemberAddress(1.0, 2.0))
//                .profileImg("image.jpg")
//                .build();
//    }
//
//    /**
//     * 회원가입 테스트
//     */
//    @Test
//    void signUp() {
//        //Given
//        SignUpDTO signUpDTO = new SignUpDTO("Test User", "testUser", "password1!", "test@example.com", new MemberAddress(1.0, 2.0), "image.jpg");
//
//        //Mocking repository behavior
//        when(memberRepository.existsByEmail(signUpDTO.getEmail())).thenReturn(false);
//        when(memberRepository.existsByLoginId(signUpDTO.getLoginId())).thenReturn(false);
//        when(memberRepository.existsByName(signUpDTO.getName())).thenReturn(false);
//
//        //Mocking passwordEncoder
//        String hashedPassword = "hashedPassword";
//        when(passwordEncoder.encode(signUpDTO.getPassword())).thenReturn(hashedPassword);
//
//        //Mocking save method
//        when(memberRepository.save(any(Member.class))).thenReturn(testMember);
//
//        //When
//        Long memberId = memberService.signUp(signUpDTO);
//
//        //Then
//        assertThat(memberId).isEqualTo(testMember.getId());
//
//    }
//
//    /**
//     * 회원가입 테스트 - 이메일이 중복될 경우
//     */
//    @Test
//    void singUp_DuplicateEmail() {
//        //Given
//        SignUpDTO signUpDTO = new SignUpDTO("testUser", "password1!", "Test User", "test@example.com", new MemberAddress(1.0, 2.0), "image.jpg");
//        when(memberRepository.existsByEmail(signUpDTO.getEmail())).thenReturn(true);
//
//        //When&Then
//        try {
//            memberService.signUp(signUpDTO);
//        } catch (DuplicateMemberException e) {
//            assertThat(e.getMessage()).isEqualTo("이 이메일은 이미 사용 중입니다.");
//        }
//
//    }
//
//    /**
//     * 로그인 성공 테스트
//     */
//    @Test
//    void login_Success() {
//        //Given
//        LoginRequest loginRequest = new LoginRequest("testUser", "password1!");
//        HttpServletRequest httpRequest = mock(HttpServletRequest.class); // Mocking HttpServletRequest
//
//        //Mocking repository to return the test member
//        when(memberRepository.findByLoginId(loginRequest.getLoginId())).thenReturn(Optional.of(testMember));
//
//        //Mocking passwordEncoder matches
//        when(passwordEncoder.matches(loginRequest.getPassword(), testMember.getPassword())).thenReturn(true);
//
//        // Mocking HttpSession
//        HttpSession session = mock(HttpSession.class);
//        when(httpRequest.getSession()).thenReturn(session);
//
//        //When
//        MemberResponseDTO responseDTO = memberService.login(loginRequest, httpRequest);
//
//        //Then
//        assertThat(responseDTO.getId()).isEqualTo(testMember.getId());
//    }
//
//    /**
//     * 로그인 실패 - 비밀번호 mismatch
//     */
//    @Test
//    void loginFail_PasswordMismatch() {
//        //Given
//        LoginRequest loginRequest = new LoginRequest("testUser", "wrongPassword");
//        HttpServletRequest httpRequest = mock(HttpServletRequest.class); // Mocking HttpServletRequest
//
//
//        //Mocking repository to return the test member
//        when(memberRepository.findByLoginId(loginRequest.getLoginId())).thenReturn(Optional.of(testMember));
//
//        //Mocking passwordEncoder matches - false
//        when(passwordEncoder.matches(loginRequest.getPassword(), testMember.getPassword())).thenReturn(false);
//
//        //When
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            memberService.login(loginRequest, httpRequest);
//        });
//
//        //Then
//        assertThat(exception.getMessage()).isEqualTo("잘못된 로그인 정보입니다.");
//    }
//
//    /**
//     * 로그인 실패 - 이유 : 유저 존재하지 않음
//     */
//    @Test
//    void loginFail_UserNotFound() {
//        //Given
//        LoginRequest loginRequest = new LoginRequest("falseUser", "password1!");
//        HttpServletRequest httpRequest = mock(HttpServletRequest.class); // Mocking HttpServletRequest
//
//        //Mocking repository to return the test member
//        when(memberRepository.findByLoginId(loginRequest.getLoginId())).thenReturn(Optional.empty());
//
//        //Mocking passwordEncoder matches - false
//        when(passwordEncoder.matches(loginRequest.getPassword(), testMember.getPassword())).thenReturn(true);
//
//        //When
//        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class, () -> {
//            memberService.login(loginRequest, httpRequest);
//        });
//
//        //Then
//        assertThat(exception.getMessage()).isEqualTo("잘못된 로그인 ID 또는 비밀번호입니다.");
//    }
//
//    /**
//     * ID로 회원 조회
//     */
//    @Test
//    void findById_Success() {
//        //Given
//        //Mocking repository to return the test member
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
//
//        //When
//        Member member = memberService.findById(1L);
//
//        //Then
//        assertThat(member).isEqualTo(testMember);
//    }
//
//    /**
//     * ID로 회원 조회 실패
//     */
//    @Test
//    void findById_Fail() {
//        //Given
//        //Mocking repository to return the test member
//        when(memberRepository.findById(1L)).thenReturn(Optional.empty());
//
//        //When
//        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class, () -> {
//            memberService.findById(1L);
//        });
//
//        //Then
//        assertThat(exception.getMessage()).isEqualTo("ID " + 1L + "로 회원을 찾을 수 없습니다.");
//    }
//
//    /**
//     * 회원 정보 조회 후 DTO로 변환
//     */
//    @Test
//    void getMemberInfo() {
//        //Given
//        //Mocking repository to return the test member
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
//
//        //When
//        MemberResponseDTO memberInfo = memberService.getMemberInfo(1L);
//
//        //Then
//        assertThat(memberInfo.getId()).isEqualTo(1L);
//        assertThat(memberInfo.getName()).isEqualTo("Test User");
//    }
//
//    /**
//     * 회원 삭제 테스트
//     */
//    @Test
//    void deleteMember() {
//        //Given
//        when(memberRepository.existsById(1L)).thenReturn(true);
//
//        //When
//        memberService.deleteMember(1L);
//
//        //Then
//        verify(memberRepository, times(1)).deleteById(1L);
//
//    }
//
//    /**
//     * 회원 정보 업데이트
//     */
//    @Test
//    void updateMemberInfo() {
//        //Given
//        MemberUpdateRequestDTO updateRequestDTO = new MemberUpdateRequestDTO("Update user", "updateEmail@example.com", "updatepassword1!", new MemberAddress(2.0,3.0));
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
//        when(memberRepository.save(any(Member.class))).thenReturn(testMember);
//
//        //When
//        MemberResponseDTO updateMemberInfo = memberService.updateMemberInfo(1L, updateRequestDTO);
//
//        //Then
//        assertThat(updateMemberInfo.getName()).isEqualTo(updateRequestDTO.getName());
//        assertThat(updateMemberInfo.getEmail()).isEqualTo(updateRequestDTO.getEmail());
//
//    }
//
//
//}