package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.graphics.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.util.*;
import java.util.*;

import com.squareup.picasso.*;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView descriptionTV, placeOfOriginTV, alsoKnownAsTV, ingredientTV;
    private ImageView imageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        descriptionTV = findViewById(R.id.description_tv);
        placeOfOriginTV = findViewById(R.id.origin_tv);
        alsoKnownAsTV = findViewById(R.id.also_known_tv);
        ingredientTV = findViewById(R.id.ingredients_tv);
        imageIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        descriptionTV.setText(sandwich.getDescription());
        placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        // Get Also Known As list
        List<String> akaList = sandwich.getAlsoKnownAs();
        // If no list, set text to "no other names"
        //TODO: get rid of view completely if list is blank
        if (akaList.size() == 0) {
            alsoKnownAsTV.setText("No other names");
        } else {
            StringBuilder akaNames = new StringBuilder();

            for(String akaName : akaList) {
                akaNames.append(akaName).append(", ");
            }
            akaNames.setLength(akaNames.length() - 2);
            alsoKnownAsTV.setText(akaNames);
        }
        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredients = new StringBuilder();

        for(String ingredient : ingredientsList) {
            ingredients.append(ingredient).append(", ");
        }
        ingredients.setLength(ingredients.length() - 2);
        ingredientTV.setText(ingredients);

    }
}
