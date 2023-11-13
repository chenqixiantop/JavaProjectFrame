package com.chenqixian.cloud.common.core.data.mongodb.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53486
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 5760097915453738435L;
    public static final int DEFAULT_PAGE_SIZE = 10;
    private int size;
    private int current;
    private int totalPage;
    private int total;
    private List<T> records;

    public Page() {
        this.current = 1;
        this.size = 10;
    }

    public Page(int currentPage, int pageSize) {
        this.current = currentPage <= 0 ? 1 : currentPage;
        this.size = pageSize <= 0 ? 1 : pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void build(List<T> rows) {
        this.setRecords(rows);
        int count = this.getTotal();
        int divisor = count / this.getSize();
        int remainder = count % this.getSize();
        this.setTotalPage(remainder == 0 ? (divisor == 0 ? 1 : divisor) : divisor + 1);
    }

    public List<T> getRecords() {
        return this.records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
