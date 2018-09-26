package fr.isep.ii3510.geographyquizwithactivities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class PlayActivity extends Activity {
    private static final String COUNTRIES_CAPITALS = "countries_capitals";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        /* Since we need at least a few pairs of countries and capitals,
        there is a file in the res/raw that contains just enough pairs to play the game.
        We only need to retrieve the contents of this file and store it in the SharedPreferences (if necessary).

        The file that we use is countries_capitals_small.csv
         */
        readFileContent();

        // This method is quite similar to the ones we saw during Lecture 2
        // The implementation is only slightly different, as I am not using any class attributes
        // Everything is done with local variables being passed to methods as arguments
        newQuestion();
    }

    private void readFileContent() {
        SharedPreferences preferences = getSharedPreferences(COUNTRIES_CAPITALS, MODE_PRIVATE);
        // We get all the values stored in this SharedPreferences
        if (preferences.getAll().size() == 0) {
            Scanner scanner = new Scanner(getResources().openRawResource(R.raw.countries_capitals_small));
            SharedPreferences.Editor editor = preferences.edit();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");
                // (key, value) -> (country, capital)
                editor.putString(split[0], split[1]);
            }
            editor.apply();
        }
    }

    /**
     * 1. We generate a random number
     * 2. We get a random country
     * 3. We update the TextView
     * 4. We generate random choices and display them in the ListView
     */
    private void newQuestion() {
        SharedPreferences sharedPreferences = getSharedPreferences(COUNTRIES_CAPITALS, MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        int index = generateRandomNumber(map.size());
        String country = selectCountry(map, index);
        updateTextView(country);
        selectChoices(map, country);
    }

    private int generateRandomNumber(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    /**
     * Because a Set in Java is not ordered (there is no index), I need to create a list first to use
     * the randomly generated number.
     * @param map       The data contained in the SharedPreferences
     * @param index     The randomly generated number
     * @return          The name of a country
     */
    private String selectCountry(Map<String, ?> map, int index) {
        List<String> keys = new ArrayList<>(map.keySet());
        return keys.get(index);
    }
    
    private void updateTextView(String country) {
        TextView textView = findViewById(R.id.country_textView);
        textView.setText(country);
    }

    /**
     * This method creates a random list of capital cities while making sure that there are exactly
     * five different choices offered to the user.
     * It also makes a call to 'updateListView'.
     * @param map       The data contained in the SharedPreferences
     * @param country   The country used for the current question
     */
    private void selectChoices(Map<String, ?> map, String country) {
        List<String> capitals = new ArrayList<>();
        capitals.add(map.get(country).toString());
        
        int random = generateRandomNumber(map.keySet().size());
        
        while (capitals.size() != 5) {
            String randomCountry = selectCountry(map, random);
            String randomCapital = map.get(randomCountry).toString();
            if (!capitals.contains(randomCapital)) {
                capitals.add(randomCapital);
            }
            random = generateRandomNumber(map.keySet().size());
        }

        Collections.shuffle(capitals);
        updateListView(map, capitals);
    }

    /**
     * Standard implementation of a ListView and its onItemClickListener
     * @param map       The data contained in the SharedPreferences
     * @param capitals  The List of capital cities that corresponds to the answers
     */
    private void updateListView(final Map<String, ?> map, final List<String> capitals) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, capitals);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = findViewById(R.id.country_textView);
                String country = textView.getText().toString();
                String capital = capitals.get(i);
                
                if (map.get(country).equals(capital)) {
                    Toast.makeText(PlayActivity.this, "Yeah! You're so awesome!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlayActivity.this, "Your Geography sucks!", Toast.LENGTH_SHORT).show();
                }
                newQuestion();
            }
        });
    }
}
