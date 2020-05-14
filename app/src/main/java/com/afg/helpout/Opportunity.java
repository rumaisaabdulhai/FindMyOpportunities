package com.afg.helpout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Comparator;

public class Opportunity  implements Parcelable, Comparable<Opportunity> {

    private String ID;
    private String title;
    private String address;
    private String contact;
    private String organizer;
    private String location;
    private String description;
    private double latitude;
    private double longitude;
    private double distance;

    public Opportunity () {
        this.ID = "ID";
        this.title = "title";
        this.address = new String();
        this.contact = new String();
        this.organizer = "organizer";
        this.location = "location";
        this.description = "description";
        this.distance = 0;
    }

    public Opportunity(String ID, String title, String organizer, String location, String description) {
        this.ID = ID;
        this.title = title;
        this.address = new String();
        this.contact = new String();
        this.organizer = organizer;
        this.location = location;
        this.description = description;
        this.distance = 0;
    }

    public Opportunity(String ID, String title, String address, String contact, String organizer, String location, String description, double latitude, double longitude) {
        this.ID = ID;
        this.title = title;
        this.address = address;
        this.contact = contact;
        this.organizer = organizer;
        this.location = location;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = 0;
    }

    public Opportunity(String ID, String title, String address, String contact, String organizer, String location, String description) {
        this.ID = ID;
        this.title = title;
        this.address = address;
        this.contact = contact;
        this.organizer = organizer;
        this.location = location;
        this.description = description;

        this.latitude = 0;
        this.longitude = 0;
        this.distance = 0;
    }

    protected Opportunity(Parcel in) {
        ID = in.readString();
        title = in.readString();
        address = in.readString();
        contact = in.readString();
        organizer = in.readString();
        location = in.readString();
        description = in.readString();
        this.distance = 0;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
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
        parcel.writeString(address);
        parcel.writeString(contact);
        parcel.writeString(organizer);
        parcel.writeString(location);
        parcel.writeString(description);
    }


    @Override
    public int compareTo(Opportunity o){
        return Double.compare(this.getDistance(), o.getDistance());
    }

}

class TitleSorter implements Comparator<Opportunity>
{
    public int compare(Opportunity o1, Opportunity o2)
    {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}

class DistanceSorter implements Comparator<Opportunity>
{
    public int compare(Opportunity o1, Opportunity o2)
    {
        return Double.compare(o1.getDistance(), o2.getDistance());
    }
}
