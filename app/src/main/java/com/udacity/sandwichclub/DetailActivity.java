package com.udacity.sandwichclub;

import android.content.Intent;

import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import java.util.*;

import com.squareup.picasso.*;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView descriptionTV, placeOfOriginTV, alsoKnownAsTV, ingredientTV,
    originLabel, alsoKnownLabel;
    private ImageView imageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        descriptionTV = findViewById(R.id.description_tv);
        placeOfOriginTV = findViewById(R.id.origin_tv);
        originLabel = findViewById(R.id.origin_label);
        alsoKnownAsTV = findViewById(R.id.also_known_tv);
        alsoKnownLabel = findViewById(R.id.also_known_label);
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

        // If no place of origin, do not display view
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originLabel.setVisibility(View.GONE);
            placeOfOriginTV.setVisibility(View.GONE);
        } else {
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        }

        // Get Also Known As list
        List<String> akaList = sandwich.getAlsoKnownAs();
        // If there's no AKA list, do not display view
        if (akaList.size() == 0) {
            alsoKnownLabel.setVisibility(View.GONE);
            alsoKnownAsTV.setVisibility(View.GONE);
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
