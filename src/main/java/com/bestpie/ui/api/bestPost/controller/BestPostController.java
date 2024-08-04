package com.bestpie.ui.api.bestPost.controller;

import com.bestpie.ui.api.bestPost.service.BestPostService;
import com.bestpie.ui.common.entity.BestPost;
import com.bestpie.ui.common.entity.PageResponse;
import com.bestpie.ui.common.entity.Rank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/rank")
    public List<Rank> getRank() throws IOException {
        return bestPostService.getRanking();
    }

    @GetMapping("/search")
    public PageResponse search(
            @RequestParam String type,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page) throws IOException {
        return bestPostService.search(type, keyword, page);
    }
}
