package com.afg.helpout.mapObjects.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.afg.helpout.OpportunitiesListActivity;
import com.afg.helpout.mapObjects.PlaceData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *This class checks runs an async task to check the user's location.
 */
public class MapQuestAPITask extends AsyncTask<String, Integer, PlaceData>{


    private Context context;
    private OpportunitiesListActivity activity;
    private static final String debugTag = "MyActivity";

    private String[] params;
    public static PlaceData place; //Store the location of the user in a PlaceData object.

    /**
     * The constructor of the async task.
     * @param activity the activity.
     */
    public MapQuestAPITask(OpportunitiesListActivity activity){
        super();
        this.activity = activity;
        this.context = this.activity.getApplicationContext();

    }

    /**
     * The doInBackground method for the async task.
     * @param params This is an array of string. However, in this task only the first parameter
     * is considered for the user's address.
     * @return the result, which is the placeData object of the user's location.
     */
    @Override
    protected PlaceData doInBackground(String...params) {
        String jsonRequest = queryMapQuest(params[0]);
        return readJSON(jsonRequest);
    }

    /**
     * Finishes the async task.
     * @param result the PlaceData object returned by the doInBackground method.
     */
    @Override
    //Retrieve User Location
    protected void onPostExecute(PlaceData result){
        this.place = result;
    }



    //IMPLEMENTS BACKGROUND TASK METHODS

    /**
     * Queries map quest for the user's location given a town and state.
     * @param userAddress the user's address, properly formatted for an HTTP query.
     * @return The PlaceData object that stores the user's location.
     */
    private String queryMapQuest(String userAddress){
        try{
            String address = userAddress;
            String urlString = MapQuestHelper.mapQuestLocationCheckURL + address;
            Log.d("MyActivity", "params url: " + urlString);

            //Connect to the server
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setReadTimeout(3000);
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(false);

            con.connect();

            int responseCode = con.getResponseCode();
            StringBuffer response = new StringBuffer();

            if (responseCode == HttpURLConnection.HTTP_OK) { // Check if the connection was a success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine; //Get the json
                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();

            } else {
                Log.d("MyActivity", "GET request did not work.");
            }

            return response.toString();

        } catch (Exception e) {
            Log.d(debugTag, "Error in queryMapQuest:" + e.toString()); //Catch any failures and return an empty string.
            return "";
        }
    }


    /**
     * Reads the resulting JSON from the HTTP request and gets the user's longitude and latitude.
     * @param result The JSON string result from the HTTP query.
     * @return The placeData object which stores the user's longitude and latitude.
     */
    private PlaceData readJSON(String result){

        PlaceData placeData;
        try {
            if (result.length() == 0) { // Check if MapQuest couldn't find the location
                Toast.makeText(context, "Unable to find the location. Please double-check your inputs.", Toast.LENGTH_LONG).show();
                placeData = new PlaceData();
            }

            else{ // Return the first location MapQuest found
                placeData = MapQuestHelper.extractJSONplaceData(result);
            }
        }

        catch(Exception e){
            placeData = new PlaceData();
            Log.d(debugTag, "Error in readJSON: " + e.toString());
        }

        return placeData;

    }
}
