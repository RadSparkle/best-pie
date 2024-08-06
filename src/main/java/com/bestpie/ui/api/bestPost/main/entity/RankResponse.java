package com.bestpie.ui.api.bestPost.main.entity;

import lombok.Data;

import java.util.List;

@Data
public class RankResponse {
    private List<Rank> ranks;
    private String timestamp;
}
