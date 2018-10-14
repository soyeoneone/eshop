package com.acidz.entity;

import java.util.List;

/**
 * Created by Acidz on 2018/9/29.
 */
public class PageBean<T> {
    private int currentPage;    //当前页码
    private int totalPage;      //总页数
    private int prePage;        //上一页
    private int nextPage;       //下一页
    private int totalRecord;    //总记录条数
    private int pageSize = 15;       //每页记录数
    private int startIndex;     //起始索引
    private String searchType;
    private String keyword;
    private List<T> products;

    public PageBean(int currentPage, String searchType, String keyword) {
        this.currentPage = currentPage;
        this.searchType = searchType;
        this.keyword = keyword;
        this.startIndex = (this.currentPage-1)*pageSize;
    }

    public void complete() {
        this.totalPage = (int) Math.ceil(this.totalRecord*1.0/pageSize);
        this.prePage = this.currentPage-1>0?this.currentPage-1:this.currentPage;
        this.nextPage =this.currentPage+1>totalPage?this.currentPage:this.currentPage+1;
    }
    public PageBean(int currentPage, int totalRecord) {
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.totalPage = (int) Math.ceil(this.totalRecord*1.0/pageSize);
        this.prePage = this.currentPage-1>0?this.currentPage-1:this.currentPage;
        this.nextPage =this.currentPage+1>totalPage?this.currentPage:this.currentPage+1;
        this.startIndex = (this.currentPage-1)*pageSize;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getProducts() {
        return products;
    }

    public void setProducts(List<T> products) {
        this.products = products;
    }
}
