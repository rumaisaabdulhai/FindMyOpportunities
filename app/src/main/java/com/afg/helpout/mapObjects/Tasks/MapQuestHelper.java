package com.afg.helpout.mapObjects.Tasks;

import android.util.Log;
import com.afg.helpout.mapObjects.PlaceData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapQuestHelper {

    public static String mapQuestLocationCheckURL = "http://open.mapquestapi.com/geocoding/v1/address?key=Hb3sWa29kDtCejsxywMUoha9IfphfmbF&location=";
    public static String debuggingLocation = "1600+Pennsylvania+Ave+NW,Washington,DC";


        public static PlaceData extractJSONplaceData(String jsonStr){
            PlaceData place = new PlaceData();
            if(jsonStr.length() == 0){ //Check if MapQuest couldn't find the location.
                return place;
            }

            else{
                try {
                    JSONObject respObj = new JSONObject(jsonStr);
                    JSONArray topLocationsObj = respObj.getJSONArray("results");
                    JSONObject res = (JSONObject) topLocationsObj.get(0);
                    JSONArray locations = res.getJSONArray("locations");


                    // Get the first JSON object.
                    // TODO:: add an algorithm to get the most likely location from the MapQuest list.
                    JSONObject location = locations.getJSONObject(0);
                    String street = res.getJSONObject("providedLocation").getString("location");

                    JSONObject latlon = location.getJSONObject("displayLatLng");
                    double lat = Double.parseDouble(latlon.getString("lat"));
                    double lon = Double.parseDouble(latlon.getString("lng"));

                    String imageUrl = location.getString("mapUrl");

                    place = new PlaceData(street, lat, lon, imageUrl);
                }

                catch(Exception e){
                    Log.v("MyActivity", "Error ocurred in extractJSONplaceData. Error message: " + e.toString());
                }
            }

            return place;
        }

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
