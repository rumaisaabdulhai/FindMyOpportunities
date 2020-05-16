package com.afg.helpout.mapObjects;

import android.util.Log;

public class PlaceData {

    private String street;
    private double latitude;
    private double longitude;
    //This is a link to a static map of the location. This isn't used right now but can be used in the UI in the future.
    private String mapUrl;

    final private double EARTH_RADIUS = 3958.8; //In MILES.

    /**
     * Constructor for a PlaceData object.
     * @param street_loc the postal address of the location (eg. 1600 Pennsylvania Ave, Washington DC)
     * @param latitude the latitude of the location.
     * @param longitude the longitude of the location.
     * @param imageUrl_loc a link to the static map (an image) of the location.
     */
    public PlaceData(String street_loc, double latitude, double longitude, String imageUrl_loc) {
            street = street_loc;
            this.latitude = latitude;
            this.longitude = longitude;
            mapUrl = imageUrl_loc;
    }

    /**
     * A constructor for a PlaceData object. This constructor initializes the street and mapURL to
     * empty strings and the latitude and longitude to 0.
     */
    public PlaceData() {
        street = "";
        this.latitude = 0;
        this.longitude = 0;
        mapUrl = "";
    }

    /**
     * Returns the latitude of the location.
     * @return The latitude of the location.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the location.
     * @param latitude The latitude of the location.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the longitude of the location.
     * @return The longitude of the location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the location.
     * @param longitude The longitude of the location.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Returns the postal address of the location. 
     * @return
     */
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    @Override
    public String toString() {
        return "PlaceData{" +
                "street='" + street + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", mapUrl='" + mapUrl + '\'' +
                '}';
    }

    public double distance(double latitude, double longitude){
        /**
         * https://www.geeksforgeeks.org/program-distance-two-points-earth/.
         */

        double lat1 = toRadians(this.latitude);
        double long1 = toRadians(this.longitude);
        double lat2 = toRadians(latitude);
        double long2 = toRadians(longitude);

        // Haversine Formula
        double dlong = long2 - long1;
        double dlat = lat2 - lat1;

        double ans = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dlong / 2), 2);

        ans = 2 * Math.asin(Math.sqrt(ans));

        // Calculate the result
        ans = ans * EARTH_RADIUS;

        return ans;

    }

    public static double distance(double lat1, double long1, double lat2, double long2){
        return new PlaceData("", lat1, long1, ""). distance(lat2, long2);
    }

    public static double toRadians(double latlong){
        return latlong/(180/Math.PI);
    }

}
