package com.adaptionsoft.games.uglytrivia;

public interface Category {
    String getNextQuestion();

    String getName();

    boolean belongsTo(int place);
}
