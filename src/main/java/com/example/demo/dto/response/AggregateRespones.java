package com.example.demo.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregateRespones<T> {
    private List<T> results;
    private int current ;
    private int pageSize;
    private int pages;
    private int total;
}
