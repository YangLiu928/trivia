package com.tysonsapps.trivia;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;

import com.tysonsapps.trivia.model.Answer;
import com.tysonsapps.trivia.model.GameData;
import com.tysonsapps.trivia.model.Question;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by jared on 4/16/15.
 */
public class Utils {

    public static GameData loadGameData(String fileName, Context context){
        ArrayList<Question> questions = new ArrayList<Question>();
        String triviaCategory = "";

        try {
            String csvString = IOUtils.toString((context.getAssets()
                    .open(fileName)));
            String[] lines = csvString.split(",\r");

            triviaCategory = lines[0];

            for (int i = 1; i < lines.length; i++) {
                Random randomGenerator = new Random();

                List<Answer> answers = new ArrayList<Answer>();
                int randomIndex;

                while (answers.size() < 3) {
                    randomIndex = randomGenerator
                            .nextInt(lines.length - 1) + 1;
                    if (randomIndex != i
                            && answers.contains(new Answer(
                            lines[randomIndex])) == false) {
                        answers.add(new Answer(lines[randomIndex], false));
                    }
                }

                // create model objects
                answers.add(new Answer(lines[i], true));
                Collections.shuffle(answers);

                Question question = new Question();
                question.setAnswers(answers);

                questions.add(question);
            }

        } catch (IOException e) {
            Log.e("generateQuestions",e.getMessage());
        }

        return new GameData(questions,triviaCategory);
    }

    public static URL parseURLFromBingJSON(String jsonString, int desiredOrientation) throws Exception{
            JSONArray imageResults = new JSONObject(jsonString)
                    .getJSONObject("d").getJSONArray("results")
                    .getJSONObject(0).getJSONArray("Image");
            if (imageResults != null && imageResults.length() > 0) {
                for (int i = 0; i < imageResults.length(); i++) {
                    JSONObject imageResult = imageResults.getJSONObject(i);
                    boolean tooBig = imageResult.getInt("FileSize") > Constants.MAX_IMAGE_FILE_SIZE_IN_BYTES;

                    if (tooBig == false) {
                        int width = imageResult.getInt("Width");
                        int height = imageResult.getInt("Height");

                        if (desiredOrientation == Configuration.ORIENTATION_PORTRAIT) {
                            if (height > width) {
                                return new URL(imageResult.getString("MediaUrl"));
                            }
                        } else if (desiredOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                            if (width > height) {
                                return new URL(imageResult.getString("MediaUrl"));
                            }
                        }
                    }
                }
            }

        return null;
    }

    public static HttpGet createImageSearchRequest(String query){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Sources", "'image'"));
        params.add(new BasicNameValuePair("Adult", "'Strict'"));
        params.add(new BasicNameValuePair("Query", "'" + query + "'"));
        params.add(new BasicNameValuePair("$format", "JSON"));

        String paramString = URLEncodedUtils.format(params, "UTF-8");

        HttpGet request = new HttpGet(Constants.BING_SEARCH_URL + "?" + paramString);
        request.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials(Constants.BING_SEARCH_API_TOKEN,
                        Constants.BING_SEARCH_API_TOKEN), "UTF-8", false));

        return request;
    }

    public static String makeImageSearchRequest(HttpGet request) throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpResponse httpResponse = httpClient.execute(request);

        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream inputStream = httpEntity.getContent();

        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");

        return writer.toString();
    }

    public static boolean saveReactionImage(Bitmap bitmap, File directory){
        File image = new File(directory,Constants.REACTION_IMAGE_FILE_NAME);

        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);

            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
