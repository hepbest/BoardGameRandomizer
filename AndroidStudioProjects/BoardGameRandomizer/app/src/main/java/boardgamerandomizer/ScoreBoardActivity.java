package boardgamerandomizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boardgamerandomizer.R;

import boardgamerandomizer.MainActivity;

public class ScoreBoardActivity extends AppCompatActivity {

    final String TEAM1_SCORE_KEY = "team1score";
    final String TEAM2_SCORE_KEY = "team2score";

    EditText team1inputField;
    EditText team2inputField;
    TextView team1outputField;
    TextView team2outputField;

    /**
     * Sets up the activity on creation.
     * Uses the team split from the Main Activity for the team labels. "Team 1 | Team 2" as default.
     * Saves the input and output boxes as object variables to simplify code.
     * @param savedInstanceState the save state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        TextView team1name = findViewById(R.id.team1Name);
        TextView team2name = findViewById(R.id.team2Name);

        Intent intent = getIntent();
        String teams = intent.getStringExtra(MainActivity.TEAMS_EXTRA);

        String[] teams_parsed = teams.split("\n");
        if (teams_parsed.length == 5) {
            team1name.setText(teams_parsed[1]);
            team2name.setText(teams_parsed[4]);
        }
        else {
            team1name.setText(R.string.team1);
            team2name.setText(R.string.team2);
        }

        team1inputField = findViewById(R.id.team1inputText);
        team2inputField = findViewById(R.id.team2inputText);
        team1outputField = findViewById(R.id.team1outputText);
        team2outputField = findViewById(R.id.team2outputText);
    }

    /**
     * Recovers a state from simple changes such as screen rotation.
     * The score text boxes are saved.
     * @param savedInstanceState the save state
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        team1outputField.setText(savedInstanceState.getString(TEAM1_SCORE_KEY));
        team2outputField.setText(savedInstanceState.getString(TEAM2_SCORE_KEY));
    }

    /**
     * Saves a state for simple changes such as screen rotation.
     * The score text boxes are saved.
     * @param outState the save state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TEAM1_SCORE_KEY, team1outputField.getText().toString());
        outState.putString(TEAM2_SCORE_KEY, team2outputField.getText().toString());

        super.onSaveInstanceState(outState);
    }

    /**
     * Updates the score of each team from the input fields (signed numeric).
     * @param view does nothing
     */
    public void addScore(View view) {
        String team1input = team1inputField.getText().toString();
        String team2input = team2inputField.getText().toString();
        String team1output = team1outputField.getText().toString();
        String team2output = team2outputField.getText().toString();

        // Read the points from the input fields. 0 on improper input.
        int team1points = 0;
        if (team1input.matches("[-+]?[\\d]+"))
            team1points = Integer.parseInt(team1input);
        int team2points = 0;
        if (team2input.matches("[-+]?[\\d]+"))
            team2points = Integer.parseInt(team2input);

        // Check if a score is already in the box and add to it.
        if (!team1output.equals("")) {
            String[] team1scores = team1output.split("\n");
            String[] team2scores = team2output.split("\n");

            team1points += Integer.parseInt(team1scores[team1scores.length-1]);
            team2points += Integer.parseInt(team2scores[team2scores.length-1]);
        }

        // Update scores.
        team1output += "\n" + team1points;
        team2output += "\n" + team2points;

        team1outputField.setText(team1output);
        team2outputField.setText(team2output);

        team1inputField.setText("");
        team2inputField.setText("");
    }
}
