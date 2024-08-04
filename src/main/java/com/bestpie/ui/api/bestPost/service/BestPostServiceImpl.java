package com.bestpie.ui.api.bestPost.service;

import com.bestpie.ui.api.bestPost.repository.BestPostRepository;
import com.bestpie.ui.common.entity.BestPost;
import com.bestpie.ui.common.entity.PageResponse;
import com.bestpie.ui.common.entity.Rank;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BestPostServiceImpl implements BestPostService {
    @Autowired
    private RestHighLevelClient client;

    private final BestPostRepository bestPostRepository;

    private final int PAGE_SIZE = 10;

    @Override
    public List<BestPost> getBestPostList(String siteName) {
        Pageable pageable = PageRequest.of(0, 10);
        return bestPostRepository.findAllBySiteNameOrderByIdDesc(siteName, pageable);
    }

    @Override
    public PageResponse search(String type, String keyword, int page) throws IOException {
        SearchRequest searchRequest = new SearchRequest("best-post");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .from(page * PAGE_SIZE)
                .size(PAGE_SIZE);

        if (keyword == null || keyword.isEmpty()) {
            sourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            String field = "title";
            if ("content".equals(type)) {
                field = "content";
            }
            sourceBuilder.query(QueryBuilders.matchQuery(field, keyword));
        }
        String[] excludes = {"content"};
        sourceBuilder.fetchSource(null, excludes);

        sourceBuilder.sort(SortBuilders.fieldSort("id").order(SortOrder.DESC));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            results.add(hit.getSourceAsMap());
        }

        return new PageResponse(page, results);
    }

    @Override
    public List<Rank> getRanking() throws IOException {
        List<Rank> rankList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("best-post");
        searchRequest.source().aggregation(
                AggregationBuilders.terms("keywords_agg").field("keywords")
        ).size(0);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Terms terms = searchResponse.getAggregations().get("keywords_agg");
        int ranking = 1;

        for (Terms.Bucket bucket : terms.getBuckets()) {
            Rank rank = new Rank();
            rank.setRank(ranking);
            rank.setKeyword(bucket.getKeyAsString());
            rankList.add(rank);
            ranking++;
        }

        return rankList;
    }
}
