package com.sparkle.bestpie.api.domain.bestPost.controller;

import com.sparkle.bestpie.api.domain.bestPost.service.BestPostService;
import com.sparkle.bestpie.common.entity.BestPost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bestPost")
@RequiredArgsConstructor
public class BestPostController {

    private final BestPostService bestPostService;

    private final String DCINSIDE = "DCINSIDE";

    private final String CLIEN = "CLIEN";

    private final String NATE = "NATE";

    private final String BOBAE = "BOBAE";

    @GetMapping("/all")
    public Map<String, List<BestPost>> getAllBestPostLists() {
        Map<String, List<BestPost>> result = new HashMap<>();
        result.put(DCINSIDE, bestPostService.getBestPostList(DCINSIDE));
        result.put(CLIEN, bestPostService.getBestPostList(CLIEN));
        result.put(NATE, bestPostService.getBestPostList(NATE));
        result.put(BOBAE, bestPostService.getBestPostList(BOBAE));
        return result;
    }
}
