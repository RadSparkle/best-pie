package com.bestpie.ui.api.domain.bestPost.service;

import com.bestpie.ui.common.entity.BestPost;

import java.util.List;

public interface BestPostService {
    List<BestPost> getBestPostList(String siteNaem);
}
