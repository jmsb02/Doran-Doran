//package com.dorandoran.backend.Post.Controller;
//
//import com.dorandoran.backend.Post.Model.PostCommandService;
//import com.dorandoran.backend.Post.dto.*;
//import com.dorandoran.backend.Post.dto.check.PostCheckDTO;
//import com.dorandoran.backend.Post.dto.check.PostCheckResponseDTO;
//import com.dorandoran.backend.Post.dto.summary.PostSummaryResponseDTO;
//import com.dorandoran.backend.Post.dto.update.PostUpdateDTO;
//import com.dorandoran.backend.Post.dto.update.PostUpdateResponseDTO;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/post")
//public class PostController {
//
//    private final PostCommandService postCommandService;
//
//    //글 작성 폼 표시
//    @GetMapping("/new")
//    public ResponseEntity<Void> savePost() {
//        return ResponseEntity.ok().build();
//    }
//
//    //글 작성
//    @PostMapping
//    public ResponseEntity<Long> createPost(@RequestBody @Valid PostRequestDTO requestDTO) {
//        Long postId = postCommandService.savePost(requestDTO);
//        return ResponseEntity.ok(postId);
//    }
//
//    //글 단일 조회
//    @GetMapping("/{postId}")
//    public ResponseEntity<PostCheckResponseDTO> getPost(@PathVariable("postId") Long postId) {
//        //게시물 조회
//        PostCheckDTO postcheck = postCommandService.findPostOne(postId);
//
//        //응답 DTO 생성
//        PostCheckResponseDTO response = new PostCheckResponseDTO(postcheck);
//        return ResponseEntity.ok(response);
//    }
//
//    //글 목록 조회
//    @GetMapping("/posts")
//    public ResponseEntity<PostSummaryResponseDTO> getPosts(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        PostSummaryResponseDTO response = postCommandService.getPosts(page, size);
//        return ResponseEntity.ok(response);
//    }
//
//    //글 업데이트
//    @PatchMapping("/{post_id}")
//    public ResponseEntity<PostUpdateResponseDTO> updatePost(@PathVariable("post_id") Long post_id, @Valid @RequestBody PostUpdateDTO postUpdateDTO) {
//        PostUpdateResponseDTO response = postCommandService.updatePost(post_id, postUpdateDTO);
//        return ResponseEntity.ok(response);
//    }
//
//    //글 삭제
//    @DeleteMapping("/{post_id}")
//    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
//        postCommandService.deletePost(postId);
//        return ResponseEntity.noContent().build();
//    }
//
//}
