package com.dorandoran.backend.Post.Controller;

import com.dorandoran.backend.Post.Model.PostCommandService;
import com.dorandoran.backend.Post.dto.PostCheckDTO;
import com.dorandoran.backend.Post.dto.PostCheckResponseDTO;
import com.dorandoran.backend.Post.dto.PostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostCommandService postCommandService;

    //글 작성 폼 표시
    @GetMapping("/new")
    public ResponseEntity<Void> savePost() {
        return ResponseEntity.ok().build();
    }

    //글 작성
    @PostMapping
    public ResponseEntity<Map<String,Object>> createPost(@RequestBody PostRequestDTO requestDTO) {
        return postCommandService.createPost(requestDTO);
    }

    //글 단일 조회
    @GetMapping("/{post_id}")
    public ResponseEntity<PostCheckResponseDTO> findPostOne(@PathVariable("post_id") Long post_id) {

        //게시물 조회
        PostCheckDTO postCheckDTO = postCommandService.findPostOne(post_id);

        //응답 DTO 생성
        PostCheckResponseDTO response = new PostCheckResponseDTO(Collections.singletonList(postCheckDTO));

        return ResponseEntity.ok(response);
    }

    //글 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Map<String,Object>> deletePost(@PathVariable Long post_id) {
        Map<String, Object> response = postCommandService.deletePost(post_id);
        return ResponseEntity.ok(response);
    }



}
