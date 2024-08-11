package com.dorandoran.backend.Comment.Model;

import com.dorandoran.backend.Comment.dto.Commentdto;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public List<Commentdto> getUserComments(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<Comment> comments = commentRepository.findAllByMemberId(memberId);
        if(comments.isEmpty()){
            throw new IllegalArgumentException("작성된 댓글이 없습니다.");
        }
        //댓글 목록을 DTO로 변환
        return comments.stream()
                .map(comment -> Commentdto.CommentToDto(comment))
                .collect(Collectors.toList());
    }
}
