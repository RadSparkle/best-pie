package com.sparkle.bestpie.api.domain.bestPost.service;

import com.sparkle.bestpie.api.domain.bestPost.repository.BestPostRepository;
import com.sparkle.bestpie.common.entity.BestPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BestPostServiceImpl implements BestPostService {

    private final BestPostRepository bestPostRepository;

    @Override
    public List<BestPost> getBestPostList() {
        return bestPostRepository.findTop20ByOrderByIdDesc();
    }
}
