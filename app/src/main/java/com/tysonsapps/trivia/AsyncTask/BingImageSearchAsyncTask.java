package com.tysonsapps.trivia.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tysonsapps.trivia.Utils;

import java.net.URL;

/**
 * Created by YangLiu on 10/5/2015.
 */
public class BingImageSearchAsyncTask extends AsyncTask<String, Integer, URL> {
    private static final String TAG =  "ImageSearchAsyncTask";
    private Context mContext;
    private ImageSearchCompletionListener mCompletionListener;
    public interface ImageSearchCompletionListener{
        public void imageUrlFound(URL url);
        public void imageUrlNotFound();
    }
    public BingImageSearchAsyncTask(Context context){
        mContext=context;
    }

    @Override
    protected URL doInBackground(String... query) {
        try {
            HttpGet httpGet = Utils.createImageSearchRequest(query[0]);
            String jsonResult = Utils.makeImageSearchRequest(httpGet);
            //detect orientation of the phone
            int desiredOrientation = mContext.getResources().getConfiguration().orientation;
            return Utils.parseURLFromBingJSON(jsonResult, desiredOrientation);
        }
        catch(Exception e){
            Log.e(TAG, "error:" + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(URL url){
        super.onPostExecute(url);
        if (mCompletionListener!=null){
            if (url !=null){
                mCompletionListener.imageUrlFound(url);
            }
            else {
                mCompletionListener.imageUrlNotFound();
            }
        }
    }

}
