package com.bestpie.ui.api.bestPost.main.entity;

import lombok.Data;

import java.util.List;

@Data
public class BestPostListResponse {
    private String siteName;

    private List<BestPost> bestPosts;
}
