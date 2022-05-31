package com.example.webclient.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record Organizations(@JsonAlias("result") List<Organization> organizations) {
}
