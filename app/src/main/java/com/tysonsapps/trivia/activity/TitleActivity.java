package com.tysonsapps.trivia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tysonsapps.trivia.AsyncTask.LoadGameDataAsyncTask;
import com.tysonsapps.trivia.R;
import com.tysonsapps.trivia.model.GameData;

public class TitleActivity extends AppCompatActivity implements LoadGameDataAsyncTask.LoadGameDataCompletionListener {

    private Button mPlayButton;
    private Button mHighScoresButton;
    private TextView mHighScoreTextView;
    private ImageView mVictorySelfieImageView;

    private final String TAG = "TitleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mHighScoresButton = (Button) findViewById(R.id.high_scores_button);
        mHighScoreTextView = (TextView) findViewById(R.id.high_score_text);
        mVictorySelfieImageView = (ImageView) findViewById(R.id.victory_selfie);
        /*
        * Alternative to ananoyous class, specify onclick in the layout XML
        * This would avoid the titleactivity.this, and use "this" directly
        *
        * */


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"play button pressed");
                LoadGameDataAsyncTask task = new LoadGameDataAsyncTask(TitleActivity.this);
                task.setCompletionListener(TitleActivity.this);
                task.execute("president.csv");

            }
        });

    }

    public void highScoreButtonPressed(View v){
        Log.d(TAG,"high score button pressed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void gameDataLoaded(GameData gameData) {
        Intent intent = new Intent(TitleActivity.this,GameActivity.class);
        intent.putExtra("gameData",gameData);//key value pair
        startActivity(intent);
    }

    @Override
    public void gameDataFailedToLoad() {
        Toast.makeText(this, R.string.gamedata_load_error, Toast.LENGTH_LONG).show();
    }
}
