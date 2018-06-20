package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.*;
import java.util.*;

public class JsonUtils {
    public static final String  NAME = "name",
                                MAIN_NAME = "mainName",
                                ALSO_KNOWN_AS = "alsoKnownAs",
                                PLACE_OF_ORIGIN = "placeOfOrigin",
                                DESCRIPTION = "description",
                                IMAGE = "image",
                                INGREDIENTS = "ingredients";



    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject root = new JSONObject(json);

            //get root object
            JSONObject name = root.getJSONObject(NAME);

            //set object values
            sandwich.setMainName(name.getString(MAIN_NAME));
            sandwich.setAlsoKnownAs(getJsonArrayAsList(name.getJSONArray(ALSO_KNOWN_AS)));
            sandwich.setPlaceOfOrigin(root.getString(PLACE_OF_ORIGIN));
            sandwich.setDescription(root.getString(DESCRIPTION));
            sandwich.setImage(root.getString(IMAGE));
            sandwich.setIngredients(getJsonArrayAsList(root.getJSONArray(INGREDIENTS)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
    private static List<String> getJsonArrayAsList(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<>();
        try {
            for(int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
