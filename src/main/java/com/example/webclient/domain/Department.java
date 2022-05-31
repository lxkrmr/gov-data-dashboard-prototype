package com.example.webclient.domain;

import java.util.List;

public record Department(String name,
                         List<Department> subordinates) { }
