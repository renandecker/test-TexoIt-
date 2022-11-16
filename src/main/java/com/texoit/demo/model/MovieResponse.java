package com.texoit.demo.model;


import java.util.List;

public class MovieResponse {

    private List<Producer> min;

    private List<Producer> max;

    public List<Producer> getMin() {
        return min;
    }

    public void setMin(List<Producer> min) {
        this.min = min;
    }

    public List<Producer> getMax() {
        return max;
    }

    public void setMax(List<Producer> max) {
        this.max = max;
    }
}
