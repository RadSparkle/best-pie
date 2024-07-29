package com.bestpie.ui.api.domain.bestPost.service;

import com.bestpie.ui.api.domain.bestPost.repository.BestPostRepository;
import com.bestpie.ui.common.entity.BestPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BestPostServiceImpl implements BestPostService {

    private final BestPostRepository bestPostRepository;

    @Override
    public List<BestPost> getBestPostList(String siteName) {
        Pageable pageable = PageRequest.of(0, 10);
        return bestPostRepository.findAllBySiteNameOrderByIdDesc(siteName, pageable);
    }
}
