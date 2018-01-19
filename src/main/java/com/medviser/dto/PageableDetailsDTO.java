package com.medviser.dto;

/**
 * Created by Longbridge on 15/11/2017.
 */
public class PageableDetailsDTO {

    public int page;

    public int size;

    public PageableDetailsDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageableDetailsDTO() {
    }
}
