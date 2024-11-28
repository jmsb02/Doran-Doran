package com.dorandoran.backend.Comment.Model;

import com.dorandoran.backend.Comment.dto.Commentdto;
import com.dorandoran.backend.member.domain.MemberRepository;
import com.dorandoran.backend.member.exception.MemberNotFoundException;
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
        //사용자 존재 여부 확인
        if(!memberRepository.existsById(memberId)){
            throw new MemberNotFoundException();
        }

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
