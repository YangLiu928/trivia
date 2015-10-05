package com.tysonsapps.trivia.model;

import java.io.Serializable;

public class Answer implements Serializable {
	private String mAnswer;
	private boolean mCorrect;

	public Answer(String answer) {
		mAnswer = answer;
	}

	public Answer(String answer, boolean correct) {
		mAnswer = answer;
		mCorrect = correct;
	}

	public String getAnswer() {
		return mAnswer;
	}

	public void setAnswer(String answer) {
		mAnswer = answer;
	}

	public boolean isCorrect() {
		return mCorrect;
	}

	public void setCorrect(boolean correct) {
		mCorrect = correct;
	}


	@Override
	public boolean equals(Object answer) {
		return mAnswer.equals(((Answer) answer).getAnswer());
	}

}
