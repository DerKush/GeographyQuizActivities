package fr.isep.ii3510.geographyquizwithactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This activity is the Launcher activity (as suggested by the AndroidManifest.xml file)
 * This activity will simply start the other activities.
 *
 * Note: We are using startActivityForResult here because it does not really make any sense to
 * do it that way
 */
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * When the Play Button is clicked, this activity starts the PlayActivity
     * @param view  The Play button
     */
    public void playQuiz(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    /**
     * When the Question Button is clicked, this activity starts the AddQuestionActivity
     * @param view  The Question Button
     */
    public void addQuestion(View view) {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
    }
}
