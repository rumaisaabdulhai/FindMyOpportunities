package com.afg.findmyopportunities;

public class Opportunity {

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadlines() {
        return headlines;
    }

    public void setHeadlines(String headlines) {
        this.headlines = headlines;
    }

    String thumb;
    String country;
    String headlines;

    public Opportunity(String thumb, String country, String headlines) {
        this.thumb = thumb;
        this.country = country;
        this.headlines = headlines;
    }

}
