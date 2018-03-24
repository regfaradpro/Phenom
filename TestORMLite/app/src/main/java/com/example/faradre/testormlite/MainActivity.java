package com.example.faradre.testormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView scoresView;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoresView = (TextView) findViewById(R.id.scoresView);
        databaseManager = new DatabaseManager(this);

        /*User jason = new User("jason", "bourne", "bourne@gamil.com");
        User james = new User("james", "bond", "bond@gamil.com");

        databaseManager.insertScore(new Score(james, 1000, new Date()));
        databaseManager.insertScore(new Score(jason, 100, new Date()));
*/

        List<Score> scores = databaseManager.readScores();
        for (Score score: scores) {
            scoresView.append(score.toString() +"\n\n");
        }

        databaseManager.close();

    }
}
