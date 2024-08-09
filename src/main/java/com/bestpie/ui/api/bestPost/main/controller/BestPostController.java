package com.bestpie.ui.api.bestPost.main.controller;

import com.bestpie.ui.api.bestPost.main.entity.RankResponse;
import com.bestpie.ui.api.bestPost.main.service.BestPostService;
import com.bestpie.ui.api.bestPost.main.entity.BestPost;
import com.bestpie.ui.api.bestPost.main.entity.PageResponse;
import com.bestpie.ui.common.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
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

    @GetMapping(value = "/rank", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<RankResponse> getRank() {
        return Flux.interval(Duration.ofSeconds(60))
                .onBackpressureDrop()
                .publishOn(Schedulers.boundedElastic())
                .map(tick -> {
                    RankResponse result = new RankResponse();
                    try {
                        result.setRanks(bestPostService.getRanking());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    result.setTimestamp(TimeUtil.getCurrentTimeRoundDown());
                    return result;
                });
    }

    @GetMapping("/search")
    public PageResponse search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page) throws IOException {
        return bestPostService.search(keyword, page);
    }
}
