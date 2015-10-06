package com.tysonsapps.trivia.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.tysonsapps.trivia.Utils;
import com.tysonsapps.trivia.model.GameData;

/**
 * Created by YangLiu on 10/5/2015.
 */
public class LoadGameDataAsyncTask extends AsyncTask<String, Integer, GameData> {
    private Context mContext;
    private LoadGameDataCompletionListener mCompletionListener;

    public LoadGameDataAsyncTask(Context context){ //Constructor
        mContext = context;
    }
    public interface LoadGameDataCompletionListener { //Two possible outcomes
        public void gameDataLoaded(GameData gameData);//Implement similar mechanism in weather app.
        public void gameDataFailedToLoad();
    }

    public void setCompletionListener(LoadGameDataCompletionListener completionListener){//setter
        mCompletionListener = completionListener;
    }
    @Override
    protected GameData doInBackground(String... filename) {

        return Utils.loadGameData(filename[0],mContext);
    }

    @Override
    protected void onPostExecute (GameData gameData){
        super.onPostExecute(gameData);
        if (mCompletionListener!=null){
            if (gameData.getQuestions()!=null && gameData.getQuestions().size()>0){
                mCompletionListener.gameDataLoaded(gameData);
            } else {
                mCompletionListener.gameDataFailedToLoad();
            }
        }
    }

}
