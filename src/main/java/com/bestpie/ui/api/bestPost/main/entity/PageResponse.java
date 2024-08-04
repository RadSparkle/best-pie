package com.bestpie.ui.api.bestPost.main.entity;

import java.util.List;
import java.util.Map;

public class PageResponse {
    private int page;
    private List<Map<String, Object>> results;

    public PageResponse(int page, List<Map<String, Object>> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, Object>> results) {
        this.results = results;
    }
}
