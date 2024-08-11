package com.dorandoran.backend.Comment.Controller;

import com.dorandoran.backend.Comment.Model.CommentCommandService;
import com.dorandoran.backend.Comment.dto.Commentdto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentCommandService commentCommandService;

    @GetMapping("/users/comments")
    public ResponseEntity<List<Commentdto>> getUserComments(HttpSession session) {
        Long memberId = (Long)session.getAttribute("memberId");
        List<Commentdto> comments = commentCommandService.getUserComments(memberId);
        return ResponseEntity.ok(comments);
    }
}
