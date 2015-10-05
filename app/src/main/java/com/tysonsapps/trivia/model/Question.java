package com.tysonsapps.trivia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
	private List<Answer> mAnswers;

	public List<Answer> getAnswers() {
		return mAnswers;
	}

	public void setAnswers(List<Answer> answers) {
		mAnswers = answers;
	}

	public Question() {
        mAnswers = new ArrayList<Answer>();
	}

	public Answer findCorrectAnswer() {
		for (Answer answer : mAnswers) {
			if (answer.isCorrect()) {
				return answer;
			}
		}

		return null;
	}
}
