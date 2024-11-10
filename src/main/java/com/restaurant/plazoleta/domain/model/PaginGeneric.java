package com.restaurant.plazoleta.domain.model;

import java.util.List;

public class PaginGeneric<T>{
    private List<T> items;
    private  Integer  currentPage;
    private  Integer size;
    private  Integer totalpages;
    private  Integer totalData;

    public PaginGeneric() {
    }

    public PaginGeneric(List<T> items, Integer currentPage, Integer size, Integer totalpages, Integer totalData) {
        this.items = items;
        this.currentPage = currentPage;
        this.size = size;
        this.totalpages = totalpages;
        this.totalData = totalData;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(Integer totalpages) {
        this.totalpages = totalpages;
    }

    public Integer getTotalData() {
        return totalData;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }
}