package com.example.webclient.adapter.in.web;

import com.example.webclient.domain.FederalMinistries;

import java.util.List;

public class DashboardResponse {
    private final String title;
    private final List<String> labels;
    private final List<Integer> data;

    private DashboardResponse(String title, List<String> labels, List<Integer> data) {
        this.title = title;
        this.labels = labels;
        this.data = data;
    }

    public static DashboardResponse create(FederalMinistries federalMinistries) {
        return new DashboardResponse("title",
                              List.of("spam", "eggs", "foo", "bar"),
                              List.of(1,2,3,4));
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Integer> getData() {
        return data;
    }
}
