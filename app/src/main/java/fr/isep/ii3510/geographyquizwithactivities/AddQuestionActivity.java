package fr.isep.ii3510.geographyquizwithactivities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddQuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
    }

    public void addQuestion(View view) {
        EditText countryTextView = findViewById(R.id.country_editText);
        EditText capitalTextView = findViewById(R.id.capital_editText);

        String country = countryTextView.getText().toString();
        String capital = capitalTextView.getText().toString();
        if (country.equals("") || capital.equals("")) {
            Toast.makeText(this, "Missing information!", Toast.LENGTH_SHORT).show();
        } else {
            addQuestion(country, capital);
            finish();
        }
    }

    /**
     * This method adds the pair (Country, Capital) in the SharedPreferences object
     * It is possible to create different "spaces" in the SharedPreferences.
     * In our case, we simply want to add this pair in the "countries_capitals" section of the SharedPreferences.
     *
     * Note: The application does not check that the country was not already present in the SharedPreferences.
     * This could be a possible improvement of the application.
     *
     * @param country   The name of the country typed in by the user
     * @param capital   The name of the corresponding capital city
     */
    private void addQuestion(String country, String capital) {
        SharedPreferences sharedPreferences = getSharedPreferences("countries_capitals", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(country, capital);
        editor.apply();
    }
}
