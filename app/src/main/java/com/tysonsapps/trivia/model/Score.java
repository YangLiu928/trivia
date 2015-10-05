package com.tysonsapps.trivia.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Score")
public class Score extends ParseObject{

    public Score(){
        super();
    }

    public Score(String name, int score, String triviaCategory){
        super();

        setName(name);
        setScore(score);
        setTriviaCategory(triviaCategory);

    }

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name",name);
	}

	public int getScore() {
		return getInt("score");
	}

	public void setScore(int score) {
		put("score",score);
	}

    public String getTriviaCategory() {
        return getString("triviaCategory");
    }

    public void setTriviaCategory(String triviaCategory) {
        put("triviaCategory",triviaCategory);
    }

	@Override
	public String toString() {
		return getScore() + " - " + getName() + " (" + getTriviaCategory() +")";
	}
}
