package com.example.webclient.adapter.in.web;

import com.example.webclient.domain.FederalMinistries;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardConverter {

    public DashboardResponse convert(FederalMinistries federalMinistries) {
        return new DashboardResponse("Title",
                                     List.of("foo", "bar"),
                                     List.of(21, 42));
    }
}
