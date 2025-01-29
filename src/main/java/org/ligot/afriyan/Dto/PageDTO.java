package org.ligot.afriyan.Dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageDTO<T> {
    private List<T> content;
    private Pageable pageable;
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
    private long numberOfElements;
    private boolean empty;
    private boolean first;
    private boolean last;

    public PageDTO(Page<T> page) {
        this.empty = page.isEmpty();
        this.numberOfElements = page.getNumberOfElements();
        this.first = page.isFirst();
        this.number = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.last = page.isLast();
        this.pageable = page.getPageable();
        this.content = page.getContent();
    }

    public List<T> getContent() {
        return content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public long getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }
}
