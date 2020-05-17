package com.afg.helpout.mapObjects.Tasks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.afg.helpout.mapObjects.PlaceData;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *This class is responsible for handling the MapQuest query formatting and results.
 */
public class MapQuestHelper {

    public static String mapQuestLocationCheckURL = "http://open.mapquestapi.com/geocoding/v1/address?key=Hb3sWa29kDtCejsxywMUoha9IfphfmbF&location=";
    public static String debuggingLocation = "1600+Pennsylvania+Ave+NW,Washington,DC";


    /**
     * Extracts the placeData object from the JSON string result.
     * @param jsonStr the JSON result from the MapQuest query.
     * @return the placeData object that stores the longitude and latitude specified in the JSON.
     */
        public static PlaceData extractJSONplaceData(Context context, String jsonStr){
            PlaceData place = new PlaceData();
            if(jsonStr.length() == 0){ //Check if MapQuest returned an empty JSON.
                Toast.makeText(context, "Unable to constrain your location at this time. Please try again later.", Toast.LENGTH_LONG).show();
                return place;
            }


            else{
                try {
                    JSONObject respObj = new JSONObject(jsonStr);
                    JSONArray topLocationsObj = respObj.getJSONArray("results");
                    JSONObject res = (JSONObject) topLocationsObj.get(0);
                    JSONArray locations = res.getJSONArray("locations");

                    // Get the first JSON object.
                    // TODO: add an algorithm to get the most likely location from the MapQuest list.
                    JSONObject location = locations.getJSONObject(0);
                    String street = res.getJSONObject("providedLocation").getString("location");

                    JSONObject latlon = location.getJSONObject("displayLatLng");
                    double lat = Double.parseDouble(latlon.getString("lat"));
                    double lon = Double.parseDouble(latlon.getString("lng"));

                    //lat=39.78373 and lon=-100.445882 are the coordinates that MapQuest returns if it couldn't find the address.
                    if(lat==39.78373 && lon==-100.445882){
                        lat=0; lon=0;
                        Toast.makeText(context, "Unable to find the location. Please double-check your inputs.", Toast.LENGTH_LONG).show();
                    }

                    String imageUrl = location.getString("mapUrl");

                    place = new PlaceData(street, lat, lon, imageUrl);
                }

                catch(Exception e){
                    Log.v("MyActivity", "Error ocurred in extractJSONplaceData. Error message: " + e.toString());
                }
            }

            return place;
        }

    /**
     * Formats a town and state input correctly for the MapQuest query.
     * @param JSONAddressInput The town and state seperated by a comma.
     * @return the formatted address input for the MapQuest query.
     */
    public static String formatAddress(String JSONAddressInput){ // Format an input address string correctly for the MapQuest query
        if(JSONAddressInput.charAt(0)=='['){
            JSONAddressInput = JSONAddressInput.replaceAll(", ", ",");
            JSONAddressInput = JSONAddressInput.replaceAll(" ","+");
            JSONAddressInput = JSONAddressInput.replaceAll("\\[", "");
            JSONAddressInput = JSONAddressInput.replaceAll("]", "");
            JSONAddressInput = JSONAddressInput.replaceAll("'", "");

        }
        else{
            JSONAddressInput = JSONAddressInput.replaceAll(", ", ",");
            JSONAddressInput = JSONAddressInput.replaceAll(" ","+");
        }

        return JSONAddressInput;
    }




}
