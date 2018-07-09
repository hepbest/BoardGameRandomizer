package boardgamerandomizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boardgamerandomizer.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String TEAMS_EXTRA = "com.example.boardgamerandomizer.TEAMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* noFunction for testing. **
    public void noFunction(View view) {
        TextView output = findViewById(R.id.outputText);
        output.setText(R.string.no_function);
    }
     /**/

    /**
     * Transitions to the ScoreBoardActivity.
     * @param view does nothing
     */
    public void scoreBoard(View view) {
        Intent intent = new Intent (this, ScoreBoardActivity.class);
        TextView output = findViewById(R.id.outputText);
        String teams = output.getText().toString();
        intent.putExtra(TEAMS_EXTRA, teams);
        startActivity(intent);
    }

    /**
     * Handles the simple die roll feature. Outputs a name from a non-number input.
     * @param view does nothing
     */
    public void rollDie(View view) {
        Random r = new Random();
        EditText input = findViewById(R.id.inputText);
        TextView output = findViewById(R.id.outputText);
        String input_text = input.getText().toString().trim(); // remove excess white-space

        if (!input_text.equals("")) {
            if (input_text.matches("[\\d]+")) // numeric input
                output.setText(String.valueOf(r.nextInt(Integer.parseInt(input_text)) + 1));
            else // name list input
            {
                String[] players = input_text.split("[\\s,]+");
                output.setText(players[r.nextInt(players.length)]);
            }
        }
        else { // blank input
            output.setText(R.string.no_input);
        }
    }

    /**
     * Divides into 2 teams. The extra player goes on team 1 if applicable.
     * Numeric input: Uses 1 to <input> as the list of names.
     * Non-numeric input: Delimited on white-space or ','.
     * @param view does nothing
     */
    public void splitTeams(View view) {
        Random r = new Random();
        EditText input = findViewById(R.id.inputText);
        TextView output = findViewById(R.id.outputText);
        String input_text = input.getText().toString().trim(); // remove excess white-space

        if (!input_text.equals("")) {
            int i = 0;
            StringBuilder output_text = new StringBuilder(); // better concatenations than String

            // On numeric input, make a string of numbers.
            if (input_text.matches("[\\d]+")) {
                int numPlayers = Integer.parseInt(input_text);
                input_text = "1";
                i = 2;
                while (i <= numPlayers) {
                    System.out.println(i);
                    input_text += " " + i;
                    i++;
                }
                i = 0;
            }

            output_text.append("Team 1\n");
            String[] players = input_text.split("[\\s,]+");
            boolean[] selected = new boolean[players.length];
            while (i < players.length) {
                int rand = r.nextInt(players.length);
                if (!selected[rand]) {
                    selected[rand] = true;
                    i++;
                    output_text.append(players[rand]);
                    if (i == (players.length - 1) / 2 + 1) // splits after half-way point
                        output_text.append("\n\nTeam 2\n");
                    else if (i != players.length)
                        output_text.append(", ");
                }
            }
            output.setText(output_text.toString());
        }
        else { // blank input
            output.setText(R.string.no_input);
        }
    }

    /**
     * Randomly orders the list.
     * Numeric input: Uses 1 to <input> as the list of names.
     * Non-numeric input: Delimited on white-space or ','.
     * @param view does nothing
     */
    public void setSeating(View view) {
        Random r = new Random();
        EditText input = findViewById(R.id.inputText);
        TextView output = findViewById(R.id.outputText);
        String input_text = input.getText().toString().trim(); // remove excess white-space

        if (!input_text.equals("")) {
            int i = 0;
            StringBuilder output_text = new StringBuilder(); // better concatenations than String
            if (input_text.matches("[\\d]+")) { // numeric input
                int numPlayers = Integer.parseInt(input_text);
                boolean[] selected = new boolean[numPlayers];
                while (i < numPlayers) {
                    int rand = r.nextInt(numPlayers);
                    if (!selected[rand]) {
                        output_text.append(rand + 1);
                        output_text.append(", ");
                        selected[rand] = true;
                        i++;
                    }
                }
            }
            else // non-numeric input
            {
                String[] players = input_text.split("[\\s,]+");
                boolean[] selected = new boolean[players.length];
                while (i < players.length) {
                    int rand = r.nextInt(players.length);
                    if (!selected[rand]) {
                        selected[rand] = true;
                        i++;
                        output_text.append(players[rand]);
                        output_text.append(", ");
                    }
                }
            }
            output.setText(output_text.substring(0, output_text.length() - 2));
        }
        else { // blank input
            output.setText(R.string.no_input);
        }
    }
}
