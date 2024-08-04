package com.bestpie.ui.api.bestPost.main.entity;

import lombok.Data;
import org.joda.time.LocalDateTime;

import java.util.List;

@Data
public class RankResponse {
    private List<Rank> ranks;
    private String timestamp;
}
