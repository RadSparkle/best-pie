package com.sparkle.bestpie.api.domain.bestPost.controller;

import com.sparkle.bestpie.api.domain.bestPost.service.BestPostService;
import com.sparkle.bestpie.common.entity.BestPost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bestPost")
@RequiredArgsConstructor
public class BestPostController {

    private final BestPostService bestPostService;

    @GetMapping("/list")
    public List<BestPost> getBestPostList() {
        return bestPostService.getBestPostList();
    }
}
