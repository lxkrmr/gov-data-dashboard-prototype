package com.example.webclient.adapter.in.web;

import java.util.List;

public record DashboardResponse(String title,
                                List<String> labels,
                                List<Integer> data) { }