package com.dorandoran.backend.Post.dto.summary;

import lombok.Getter;

import java.util.List;

@Getter
public class PostSummaryResponseDTO {

    private int page;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private List<PostSummaryDTO> posts;

    public PostSummaryResponseDTO(int page, int pageSize, long totalItems, int totalPages, List<PostSummaryDTO> posts) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.posts = posts;
    }
}
