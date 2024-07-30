package com.dorandoran.backend.Post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostCheckResponseDTO {

    private int page;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private List<PostCheckDTO> posts;

    public PostCheckResponseDTO(List<PostCheckDTO> posts) {
        this.posts = posts;
    }

    public PostCheckResponseDTO(int page, int pageSize, long totalItems, int totalPages, List<PostCheckDTO> posts) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.posts = posts;
    }
}
