package com.sparkle.bestpie.api.domain.bestPost.repository;

import com.sparkle.bestpie.common.entity.BestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestPostRepository extends JpaRepository<BestPost, Long> {
    List<BestPost> findTop20ByOrderByIdDesc();
}
