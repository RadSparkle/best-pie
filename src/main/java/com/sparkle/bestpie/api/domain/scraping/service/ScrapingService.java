package com.sparkle.bestpie.api.domain.scraping.service;

import com.sparkle.bestpie.common.entity.BestPost;

public interface ScrapingService {

    Long savePost(BestPost bestPost);
}
