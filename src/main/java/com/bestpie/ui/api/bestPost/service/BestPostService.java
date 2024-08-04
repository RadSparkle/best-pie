package com.bestpie.ui.api.bestPost.service;

import com.bestpie.ui.common.entity.BestPost;
import com.bestpie.ui.common.entity.PageResponse;
import com.bestpie.ui.common.entity.Rank;

import java.io.IOException;
import java.util.List;


public interface BestPostService {
    List<BestPost> getBestPostList(String siteNaem);

    PageResponse search(String type, String keyword, int page) throws IOException;

    List<Rank> getRanking() throws IOException;
}
