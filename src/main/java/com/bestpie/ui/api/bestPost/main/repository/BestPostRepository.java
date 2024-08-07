package com.bestpie.ui.api.bestPost.main.repository;

import com.bestpie.ui.api.bestPost.main.entity.BestPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestPostRepository extends JpaRepository<BestPost, Long> {

    List<BestPost> findAllBySiteNameOrderByIdDesc(String siteName, Pageable pageable);
}
