package com.tysonsapps.trivia.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jared on 4/19/15.
 */
public class GameData implements Serializable {
    private List<Question> mQuestions;
    private String mTriviaCategory;

    public GameData(){
        super();
    }

    public GameData(List<Question> questions, String triviaCategory){
        mQuestions = questions;
        mTriviaCategory = triviaCategory;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> questions) {
        mQuestions = questions;
    }

    public String getTriviaCategory() {
        return mTriviaCategory;
    }

    public void setTriviaCategory(String triviaCategory) {
        mTriviaCategory = triviaCategory;
    }
}
