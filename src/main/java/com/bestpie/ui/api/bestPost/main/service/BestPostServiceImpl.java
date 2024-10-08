package com.bestpie.ui.api.bestPost.main.service;

import com.bestpie.ui.api.bestPost.main.repository.BestPostRepository;
import com.bestpie.ui.api.bestPost.main.entity.BestPost;
import com.bestpie.ui.api.bestPost.main.entity.PageResponse;
import com.bestpie.ui.api.bestPost.main.entity.Rank;
import com.bestpie.ui.common.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
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
    public PageResponse search(String keyword, int page) throws IOException {
        SearchRequest searchRequest = new SearchRequest("best-post");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .from(page * PAGE_SIZE)
                .size(PAGE_SIZE);

        if (keyword == null || keyword.isEmpty()) {
            sourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.should(QueryBuilders.matchQuery("title", keyword));
            boolQuery.should(QueryBuilders.matchQuery("content", keyword));
            sourceBuilder.query(boolQuery);
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
        String nowTime = TimeUtil.getCurrentTimeYYYYMMDD();
        String rankStartTime = nowTime + "0000";
        String rankEndTime = nowTime + "2359";

        // SearchRequest 객체 생성
        SearchRequest searchRequest = new SearchRequest("best-post");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 날짜 범위 쿼리 추가
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("scraped_at")
                .gte(rankStartTime)
                .lt(rankEndTime);
        sourceBuilder.query(rangeQuery);

        // 집계 설정
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("keywords_agg").field("keywords");
        sourceBuilder.aggregation(aggregationBuilder);

        searchRequest.source(sourceBuilder);
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
