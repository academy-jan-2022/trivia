package com.adaptionsoft.games.uglytrivia;

public interface ICategory {
    String getNextQuestion();

    void createQuestions();

    String getName();
}
