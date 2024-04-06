package com.tyfzw.booksmanage.gateway.api.web.response;

import lombok.Data;

import java.util.List;

@Data
public class DataGridDataSource<T> {

    private long total;

    private List<T> rows;

}
