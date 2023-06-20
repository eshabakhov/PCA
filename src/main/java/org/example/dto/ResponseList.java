package org.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseList<T> {
    private List<T> list;
    private Integer total;
    private Integer currentPage;
    private Integer pageSize;
}
