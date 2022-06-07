package lx.krmr.dashboard.adapter.in.web.model.types;

import java.util.List;

public record DashboardResponse(String title,
                                List<String> labels,
                                List<Integer> data) { }
