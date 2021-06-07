package com.cars_now.backend.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultList<T> {

    private List<T> list;
    private long totalCount;

}

