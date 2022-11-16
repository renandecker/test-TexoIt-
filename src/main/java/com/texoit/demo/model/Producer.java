package com.texoit.demo.model;



public class Producer  implements Comparable<Producer>{

    private String producer;

    private Integer interval;

    private Integer previousWin;

    private Integer followingWin;

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(Integer previousWin) {
        this.previousWin = previousWin;
    }

    public Integer getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(Integer followingWin) {
        this.followingWin = followingWin;
    }

    @Override
    public int compareTo(Producer o) {
        if (this.interval > o.getInterval()) {
            return -1;
        } if (this.interval < o.getInterval()) {
            return 1;
        }
        return 0;
    }
}
