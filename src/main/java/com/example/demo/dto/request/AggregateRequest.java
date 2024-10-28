package com.example.demo.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class AggregateRequest {
    private int limit;
    private int start;
    private String sortField;
    private String sortType;

    private List<Condition> conditions;

    public void setStart(int start) {
        this.start = start;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortType(String  sortType) {
        this.sortType = sortType;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

 
    @Getter
    public static class Condition {
        private String key;
        private List<String> value;

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
