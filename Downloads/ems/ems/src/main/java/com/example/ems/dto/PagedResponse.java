package com.example.ems.dto;



import java.util.List;

public class PagedResponse<T> {
    public int page;
    public int size;
    public int totalPages;
    public long totalElements;
    public List<T> content;

    public PagedResponse(int page, int size, int totalPages, long totalElements, List<T> content) {
        this.page = page; this.size = size; this.totalPages = totalPages; this.totalElements = totalElements; this.content = content;
    }
}
