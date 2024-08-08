package com.bestpie.ui.api.bestPost.main.service;

import com.bestpie.ui.api.bestPost.main.entity.BestPost;
import com.bestpie.ui.api.bestPost.main.entity.PageResponse;
import com.bestpie.ui.api.bestPost.main.entity.Rank;

import java.io.IOException;
import java.util.List;


public interface BestPostService {
    List<BestPost> getBestPostList(String siteNaem);

    PageResponse search(String keyword, int page) throws IOException;

    List<Rank> getRanking() throws IOException;
}
