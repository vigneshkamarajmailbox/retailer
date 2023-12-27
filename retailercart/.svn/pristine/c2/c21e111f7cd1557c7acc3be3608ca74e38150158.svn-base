package com.botree.retailerssfa.support;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectionsJSONParser {

    private static final String TAG = DirectionsJSONParser.class.getSimpleName();
    private static final String ROUTES = "routes";
    private static final String STEPS = "steps";
    private static final String POLYLINE = "polyline";
    private static final String POINTS = "points";

    /**
     * Receives a JSONObject and returns a list of lists containing latitude and longitude
     */
    public List<List<HashMap>> parse(JSONObject jObject) {

        List<List<HashMap>> routes = new ArrayList<>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {

            jRoutes = jObject.getJSONArray(ROUTES);
            Log.d(TAG, "Routes : " + jRoutes.toString());

            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<>();

                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray(STEPS);

                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline;
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get(POLYLINE)).get(POINTS);
                        List list = decodePoly(polyline);

                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "parse: " + e.getMessage(), e);
        } catch (Exception e) {
            //ignored
        }

        return routes;
    }

    public Map<String, Object> parseNew(JSONObject jObject) {

        HashMap<String, Object> routes = new HashMap<>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        String jDistance;
        String jDuration;

        try {

            jRoutes = jObject.getJSONArray(ROUTES);
            Log.d(TAG, "Routes : " + jRoutes.toString());

            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<>();

                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray(STEPS);
                    jDistance = ((JSONObject) jLegs.get(j)).getJSONObject("distance").getString("text");
                    jDuration = ((JSONObject) jLegs.get(j)).getJSONObject("duration").getString("text");

                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline;
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get(POLYLINE)).get(POINTS);
                        List list = decodePoly(polyline);

                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.put("path_points", path);
                    routes.put("distance", jDistance);
                    routes.put("duration", jDuration);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "parseNew: " + e.getMessage(), e);
        } catch (Exception e) {
            //ignored
        }

        return routes;
    }


    public List<List<HashMap<String, String>>> parseMultipleRoute(JSONObject jObject) {
        List<List<HashMap<String, String>>> routes = new ArrayList<>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        try {
            jRoutes = jObject.getJSONArray(ROUTES);
            /* Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> path = new ArrayList<>();

                /* Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray(STEPS);

                    /* Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline;
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                .get(k)).get(POLYLINE)).get(POINTS);
                        List<LatLng> list = decodePoly(polyline);

                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("lat",
                                    Double.toString(list.get(l).latitude));
                            hm.put("lng",
                                    Double.toString(list.get(l).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "parseMultipleRoute: " + e.getMessage(), e);
        } catch (Exception e) {
            //ignored
        }
        return routes;
    }

    /**
     * Method Courtesy :
     * jeffreysambells.com/2010/05/27
     * /decoding-polylines-from-google-maps-direction-api-with-java
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0;
        int len = encoded.length();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng(((double) lat / 1E5),
                    ((double) lng / 1E5));
            poly.add(p);
        }
        return poly;
    }
}