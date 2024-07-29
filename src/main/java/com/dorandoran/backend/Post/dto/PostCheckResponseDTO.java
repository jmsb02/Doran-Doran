package com.dorandoran.backend.Post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostCheckResponseDTO {
    private List<PostCheckDTO> posts;

    public PostCheckResponseDTO(List<PostCheckDTO> posts) {
        this.posts = posts;
    }

}
