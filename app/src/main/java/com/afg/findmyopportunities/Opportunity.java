package com.afg.findmyopportunities;

public class Opportunity {

    private String title;
    private String address[];
    private String contact[];
    private String organizer;
    private String location;
    private String description;

    public Opportunity (String[] address, String[] contact, String description, String organizer, String title, String location) {
        this.title = title;
        this.address = address;
        this.contact = contact;
        this.organizer = organizer;
        this.location = location;
        this.description = description;
    }

    public Opportunity () {
        this.title = "title";
        this.address = new String[] {"address"};
        this.contact = new String[] {"address"};
        this.organizer = "organizer";
        this.location = "location";
        this.description = "description";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String[] getContact() {
        return contact;
    }

    public void setContact(String[] contact) {
        this.contact = contact;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
