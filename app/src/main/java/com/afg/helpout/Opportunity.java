package com.afg.helpout;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The Opportunity Class
 * Models a volunteer opportunity given an organizer, title, description, address,
 *contact information, and longitude/latitude coordinates. The distance between a user
 * and an opportunity is initialized to 0.
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
     * Constructs an opportunity without given any information.
     */
    public Opportunity () {
        this.ID = "ID";
        this.title = "title";
        this.address = new String();
        this.contact = new String();
        this.organizer = "organizer";
        this.location = "location";
        this.description = "description";
        this.longitude = 0; this.latitude = 0;
        this.distance = 0;
    }

    /**
     * Constructs an opportunity objet given the ID, title, organizer, location, and description.
     * @param ID The ID of the opportunity.
     * @param title The title of the opportunity.
     * @param organizer The organizer of the opportunity.
     * @param location The location of the opportunity.
     * @param description The description of the opportunity.
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
        latitude = 0; longitude = 0;
    }

    /**
     * Constructs an opportunity objet given the ID, title, organizer, location, and description,
     * and latitude/longitude coordinates.
     * @param ID The ID of the opportunity.
     * @param title The title of the opportunity.
     * @param organizer The organizer of the opportunity.
     * @param location The location of the opportunity.
     * @param description The description of the opportunity.
     * @param latitude The latitude of the opportunity.
     * @param longitude The longitude of the opportunity.
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
     * Gets the latitude of the opportunity.
     * @return The latitude.
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
     * Gets the latitude of the opportunity.
     * @return The longitude.
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
     * Gets the latitude of the opportunity.
     * @return The distance.
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
     * Gets the ID of the opportunity.
     * @return The ID.
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
     * Gets the title of the opportunity.
     * @return The title.
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
     * Gets the address of the opportunity.
     * @return The address.
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
     * Gets the contact of the opportunity.
     * @return The contact.
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
     * Gets the organizer of the opportunity.
     * @return The organizer.
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
     * Gets the location of the opportunity.
     * @return The location.
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
     * Gets the description of the opportunity.
     * @return The description.
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
     * TODO: What does this method do?
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
     *Compares two opportunities based on the distance between two
     * opportunities.
     * @param o
     * @return An integer based on the ranking of the opportunity object. If the distance is less than
     * the given opportunity's distance, this method will return a negative number. If the distances are
     * the same, the method will return 0. If the distance is greater than the given opportunity's distance,
     * this method will return a positive number. Keep in mind that upon creating the opportunity object,
     * every opportunity has a distance of 0 until the distance is set.
     */
    @Override
    public int compareTo(Opportunity o){
        return Double.compare(this.getDistance(), o.getDistance());
    }

}

/**
 * This class implements a custom Comparator
 *to compare lists of opportunities based on the title of the opportunity.
 */
class TitleSorter implements Comparator<Opportunity>
{
    /**
     *This method compares two opportunities based on their title.
     * @param o1 the first opportunity
     * @param o2 the second opportunity
     * @return An integer based on the ranking of the opportunity object's title lexicographically.
     * If the title is less than the given opportunity's title, this method will return a negative number. If the titles are
     * the same, the method will return 0. If the title is greater than the given opportunity's title,
     * this method will return a positive number.
     */
    public int compare(Opportunity o1, Opportunity o2)
    {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}

/**
 * This class implements a custom Comparator
 *to compare lists of opportunities based on distances.
 */
class DistanceSorter implements Comparator<Opportunity>
{
    /**
     *This method compares two opportunities based on their distance.
     * @param o1 the first opportunity
     * @param o2 the second opportunity
     * @return An integer based on the ranking of the opportunity object. If the distance is less than
     * the given opportunity's distance, this method will return a negative number. If the distances are
     * the same, the method will return 0. If the distance is greater than the given opportunity's distance,
     * this method will return a positive number. Keep in mind that upon creating the opportunity object,
     * every opportunity has a distance of 0 until the distance is set.
     */
    public int compare(Opportunity o1, Opportunity o2)
    {
        return Double.compare(o1.getDistance(), o2.getDistance());
    }
}
