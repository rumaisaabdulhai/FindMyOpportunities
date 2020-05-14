package com.afg.helpout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The Opportunity Class
 *
 * TODO: Complete Documentation
 *
 */
public class Opportunity  implements Parcelable, Comparable<Opportunity> {

    // Instance Variables
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

    /**
     *   TODO: Complete Documentation
     */
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

    /**
     * @param ID
     * @param title
     * @param organizer
     * @param location
     * @param description
     */
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

    /**
     * @param ID
     * @param title
     * @param address
     * @param contact
     * @param organizer
     * @param location
     * @param description
     * @param latitude
     * @param longitude
     */
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

    /**
     * @param ID
     * @param title
     * @param address
     * @param contact
     * @param organizer
     * @param location
     * @param description
     */
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

    /**
     * @param in
     */
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

    /**
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * @param organizer
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * TODO Complete Documentation
     *
     * @param parcel
     * @param i
     */
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


    /**
     * TODO Complete Documentation
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Opportunity o){
        return Double.compare(this.getDistance(), o.getDistance());
    }

}

/**
 * TODO Complete Documentation
 *
 */
class TitleSorter implements Comparator<Opportunity>
{
    /**
     * TODO Complete Documentation
     *
     * @param o1
     * @param o2
     * @return
     */
    public int compare(Opportunity o1, Opportunity o2)
    {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}

/**
 * TODO Complete Documentation
 */
class DistanceSorter implements Comparator<Opportunity>
{
    /**
     *
     * TODO Complete Documentation
     *
     * @param o1
     * @param o2
     * @return
     */
    public int compare(Opportunity o1, Opportunity o2)
    {
        return Double.compare(o1.getDistance(), o2.getDistance());
    }
}
