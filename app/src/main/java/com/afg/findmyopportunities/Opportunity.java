package com.afg.findmyopportunities;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Opportunity  implements Parcelable {

    private String ID;
    private String title;
    private ArrayList<String> address;
    private ArrayList<String> contact;
    private String organizer;
    private String location;
    private String description;

    public Opportunity () {
        this.ID = "ID";
        this.title = "title";
        this.address = new ArrayList<>();
        this.contact = new ArrayList<>();
        this.organizer = "organizer";
        this.location = "location";
        this.description = "description";
    }

    public Opportunity(String ID, String title, String organizer, String location, String description) {
        this.ID = ID;
        this.title = title;
        this.address = new ArrayList<>();
        this.contact = new ArrayList<>();
        this.organizer = organizer;
        this.location = location;
        this.description = description;
    }

    public Opportunity(String ID, String title, ArrayList<String> address, ArrayList<String> contact, String organizer, String location, String description) {
        this.ID = ID;
        this.title = title;
        this.address = address;
        this.contact = contact;
        this.organizer = organizer;
        this.location = location;
        this.description = description;
    }

    protected Opportunity(Parcel in) {
        ID = in.readString();
        title = in.readString();
        address = in.createStringArrayList();
        contact = in.createStringArrayList();
        organizer = in.readString();
        location = in.readString();
        description = in.readString();
    }

    public static final Creator<Opportunity> CREATOR = new Creator<Opportunity>() {
        @Override
        public Opportunity createFromParcel(Parcel in) {
            return new Opportunity(in);
        }

        @Override
        public Opportunity[] newArray(int size) {
            return new Opportunity[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    public ArrayList<String> getContact() {
        return contact;
    }

    public void setContact(ArrayList<String> contact) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    //    Assists in sending object to DisplayOpportunityActivity
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(title);
        parcel.writeStringList(address);
        parcel.writeStringList(contact);
        parcel.writeString(organizer);
        parcel.writeString(location);
        parcel.writeString(description);
    }

}
