package com.sparkle.bestpie.api.domain.scraping.repository;

import com.sparkle.bestpie.common.entity.BestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapingRepository extends JpaRepository<BestPost, Long> {
    boolean existsByTitle(String title);
}
