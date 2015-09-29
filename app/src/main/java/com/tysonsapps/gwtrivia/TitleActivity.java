package com.tysonsapps.gwtrivia;

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

public class TitleActivity extends AppCompatActivity {

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

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"play button pressed");

                Intent intent = new Intent(TitleActivity.this,GameActivity.class);
                startActivity(intent);
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
}
