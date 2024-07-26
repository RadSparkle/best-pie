package com.sparkle.bestpie.api.domain.scraping.service;

import com.sparkle.bestpie.api.domain.scraping.repository.ScrapingRepository;
import com.sparkle.bestpie.common.entity.BestPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScrapingServiceImpl implements ScrapingService {

    private final ScrapingRepository scrapingRepository;

    /**
     * Saves a new scrap entry if it doesn't already exist.
     * @param bestPost The community to be saved.
     * @return The ID of the saved community, or {@code null} if a duplicate was found.
     */
    @Override
    public Long savePost(BestPost bestPost) {
        if(!scrapingRepository.existsByTitle(bestPost.getTitle())){
            BestPost savedBestPost = scrapingRepository.save(bestPost);
            log.info("{} | {}", bestPost.getSiteName(), bestPost.getTitle());
            return savedBestPost.getId();
        }
        return null;
    }
}
