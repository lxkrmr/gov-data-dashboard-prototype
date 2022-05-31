package com.example.webclient.adapter.out.govdata;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record GovDataResponse(@JsonAlias("result") List<Organization> organizations) {
}
